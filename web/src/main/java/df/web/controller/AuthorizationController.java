package df.web.controller;

import df.base.jpa.UserRepository;
import df.web.property.HttpSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/secure")
public class AuthorizationController {

    private final HttpSecurityProperties properties;

    @Autowired
    private UserRepository users;

    public AuthorizationController(HttpSecurityProperties properties) {
        this.properties = properties;
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView emailPage() {
        ModelAndView mav                   = new ModelAndView("auth/login-page");
        String       authorizationEndpoint = properties.getOAuth2().getAuthorizationEndpoint();
        String       github                = authorizationEndpoint + "/github";
        String       google                = authorizationEndpoint + "/google";

        mav.addObject("security", properties);
        mav.addObject("github", github);
        mav.addObject("google", google);

        mav.addObject("properties", properties.getFormLogin());

        return mav;
    }

}
