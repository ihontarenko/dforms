package df.web.controller.user;

import df.base.security.UserInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user/role")
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {

    @GetMapping("/index")
    public ModelAndView index(@AuthenticationPrincipal UserInfo principal) {
        ModelAndView mav = new ModelAndView("user/role");

        mav.addObject("principal", principal);

        return mav;
    }

    public ModelAndView modify(@AuthenticationPrincipal UserInfo principal) {
        ModelAndView mav = new ModelAndView("user/role");

        mav.addObject("principal", principal);

        return mav;
    }



}
