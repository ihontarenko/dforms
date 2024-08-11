package df.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Set;

@SuppressWarnings({"unused"})
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Set<Locale> locales;

    public GlobalExceptionHandler(Set<Locale> locales) {
        this.locales = locales;
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
