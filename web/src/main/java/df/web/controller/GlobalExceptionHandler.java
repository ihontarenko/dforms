package df.web.controller;

import df.base.property.ApplicationProperties;
import df.web.common.flash.FlashMessageService;
import df.web.common.flash.FlashMessageType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Set;

@SuppressWarnings({"unused"})
@ControllerAdvice
public class GlobalExceptionHandler {

    private final ApplicationProperties properties;
    private final Set<Locale> locales;
    private final FlashMessageService flashMessageService;

    public GlobalExceptionHandler(ApplicationProperties properties, Set<Locale> locales, FlashMessageService flashMessageService) {
        this.properties = properties;
        this.locales = locales;
        this.flashMessageService = flashMessageService;
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public void httpRequestMethodNotSupportedException(HttpServletRequest request, HttpServletResponse response,
                                                       HttpRequestMethodNotSupportedException exception) throws IOException {
        if (!response.isCommitted()) {
            FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
            flashMessageService.addFlashMessage(flashMap, exception.getMessage(), FlashMessageType.ERROR);
            RequestContextUtils.saveOutputFlashMap(properties.getHomeUrl(), request, response);
            response.sendRedirect(properties.getHomeUrl());
        }
    }

    @ExceptionHandler({Exception.class, RuntimeException.class, ClassCastException.class})
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
    public void modelMapHandler(ModelMap map) {
        map.addAttribute("locales", locales);
    }

}
