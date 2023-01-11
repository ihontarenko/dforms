package io.startform.web.controller;

import io.startform.web.property.HttpSecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/security")
public class AuthorizationController {

    private final HttpSecurityProperties properties;

    public AuthorizationController(HttpSecurityProperties properties) {
        this.properties = properties;
    }

    @GetMapping("/check")
    @ResponseBody
    public String check(Principal principal) {
        return principal != null ? principal.getName() : "NULL";
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("login");

        mv.addObject("security", properties);

        return mv;
    }

}
