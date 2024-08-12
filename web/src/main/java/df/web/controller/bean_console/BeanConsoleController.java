package df.web.controller.bean_console;

import df.web.common.flash.FlashMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/bean-console")
public class BeanConsoleController {

    private final FlashMessageService flashMessage;

    public BeanConsoleController(FlashMessageService flashMessage) {
        this.flashMessage = flashMessage;
    }

    @GetMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("bean_console/index");
    }

}
