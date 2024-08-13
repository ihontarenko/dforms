package df.web.controller;

import df.base.security.UserInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserAdminController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = {"/index", "/{formId}/edit"})
    public ModelAndView index(@AuthenticationPrincipal UserInfo principal) {
        ModelAndView mav = new ModelAndView("users/index");

        mav.addObject("principal", principal);

        return mav;
    }

}
