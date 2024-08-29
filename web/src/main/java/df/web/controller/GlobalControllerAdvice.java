package df.web.controller;

import df.base.internal.breadcrumb.BreadcrumbService;
import df.base.property.ApplicationProperties;
import df.base.security.UserInfo;
import df.base.service.RedirectAware;
import df.base.service.ResourceNotFoundException;
import df.web.common.flash.FlashMessageService;
import io.pebbletemplates.pebble.error.PebbleException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.ModelMap;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Set;

import static df.web.common.flash.FlashMessage.error;

@SuppressWarnings({"unused"})
@ControllerAdvice
public class GlobalControllerAdvice {

    private final ApplicationProperties properties;
    private final Set<Locale>           locales;
    private final BreadcrumbService     breadcrumbs;
    private final FlashMessageService   flash;

    public GlobalControllerAdvice(ApplicationProperties properties, Set<Locale> locales,
                                  BreadcrumbService breadcrumbs, FlashMessageService flash) {
        this.properties = properties;
        this.locales = locales;
        this.breadcrumbs = breadcrumbs;
        this.flash = flash;
    }

    @ExceptionHandler({
            PebbleException.class,
            HttpRequestMethodNotSupportedException.class,
            AccessDeniedException.class,
            NoResourceFoundException.class,
            ResourceNotFoundException.class
    })
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public void httpRequestMethodNotSupportedException(HttpServletRequest request, HttpServletResponse response,
                                                       Exception exception) throws IOException {
        if (!response.isCommitted()) {
            FlashMap flashMap    = RequestContextUtils.getOutputFlashMap(request);
            String   redirectUrl = properties.getHomeUrl();

            flash.addMessage(flashMap, error(exception.getMessage()));

            if (exception instanceof RedirectAware redirectAware && redirectAware.hasRedirectUrl()) {
                redirectUrl = redirectAware.getRedirectUrl();
            }

            RequestContextUtils.saveOutputFlashMap(redirectUrl, request, response);

            response.sendRedirect(redirectUrl);
        }
    }

    @ExceptionHandler({
            Exception.class,
            RuntimeException.class,
            ClassCastException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView globalException(Exception exception) {
        return buildMAV(exception);
    }

    private ModelAndView buildMAV(final Throwable throwable) {
        ModelAndView  mav       = new ModelAndView("exception");
        StringBuilder builder   = new StringBuilder(getStackTrace(throwable));
        Throwable     exception = throwable.getCause();

        while (exception != null) {
            builder.append("\n\n").append(getStackTrace(exception));
            exception = exception.getCause();
        }

        mav.addObject("stackTrace", builder.toString());
        mav.addObject("class", throwable.getClass().getName());
        mav.addObject("message", throwable.getMessage());

        builder.append(getStackTrace(throwable));

        return mav;
    }

    private String getStackTrace(Throwable throwable) {
        StringWriter writer = new StringWriter();

        throwable.printStackTrace(new PrintWriter(writer));

        return writer.toString();
    }

    @ModelAttribute
    public void modelMapHandler(HttpServletRequest request, ModelMap map, @AuthenticationPrincipal UserInfo principal) {
        map.addAttribute("breadcrumbs", breadcrumbs.getBreadcrumbsForRequest(request));
        map.addAttribute("locales", locales);
        map.addAttribute("principal", principal);
    }

}
