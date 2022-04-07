package io.startform.web.controller;

import io.startform.parent.property.HttpSecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/security")
public class AuthorizationController {

    private HttpSecurityProperties properties;

    public AuthorizationController(HttpSecurityProperties properties) {
        this.properties = properties;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("login");

        mv.addObject("security", properties);

        return mv;
    }

}
