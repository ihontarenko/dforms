package df.web.controller;

import df.base.security.UserInfo;
import df.web.common.flash.FlashMessage;
import df.web.common.flash.FlashMessageService;
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

    public HomeController(FlashMessageService flashMessage) {
        this.flashMessage = flashMessage;
    }

    @GetMapping("/home")
    public ModelAndView index(
            @AuthenticationPrincipal UserInfo principal,
            @RequestParam(name = "authorizationStatus", required = false) String status,
            RedirectAttributes attributes
    ) {
        ModelAndView mav = new ModelAndView("index/home");

        if (status != null) {
            flashMessage.addMessage(attributes, "Welcome on board %s you are authorized using '%s'!"
                    .formatted(principal.getName(), status.toUpperCase()), FlashMessage.Type.SUCCESS);
            mav.setViewName("redirect:/index/home");
        }

        return mav;
    }

}
