package df.web.controller;

import df.base.common.validation.Validation;
import df.base.security.UserInfo;
import df.web.common.flash.FlashMessage;
import df.web.common.flash.FlashMessageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/index")
public class HomeController {

    private final FlashMessageService flashMessage;
    private final Validation commonValidation;

    public HomeController(FlashMessageService flashMessage, @Qualifier("commonValidation") Validation commonValidation) {
        this.flashMessage = flashMessage;
        this.commonValidation = commonValidation;
    }

    @GetMapping("/home")
    public ModelAndView index(
            @AuthenticationPrincipal UserInfo principal,
            @RequestParam(name = "authorizationStatus", required = false) String status,
            RedirectAttributes attributes
    ) {
        ModelAndView mav = new ModelAndView("index/home");

        commonValidation.validate(null);

        if (status != null) {
            flashMessage.addMessage(attributes, "Welcome on board %s you are authorized using '%s'!"
                    .formatted(principal.getName(), status.toUpperCase()), FlashMessage.Type.SUCCESS);
            mav.setViewName("redirect:/index/home");
        }

        return mav;
    }

}
