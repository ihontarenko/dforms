package df.web.controller;

import df.parent.jpa.User;
import df.parent.jpa.UserListRepository;
import df.parent.library.i18n.Translator;
import df.web.property.HttpSecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/secure")
public class AuthorizationController {

    private static final Logger                 LOG = LoggerFactory.getLogger(AuthorizationController.class);
    private final        Translator             translator;
    private final        HttpSecurityProperties properties;

    @Autowired
    private UserListRepository users;

    public AuthorizationController(Translator translator, HttpSecurityProperties properties) {
        this.translator = translator;
        this.properties = properties;
    }

    @GetMapping("/check")
    @ResponseBody
    public String authorizationStatus(Principal principal) {
        System.out.println(users.findAll());

        User user = new User();

        user.setLogin("IVAN");
        user.setPassword("IVAN");
        user.setEnabled(false);

        users.save(user);

        return "PRINCIPAL: %s".formatted(principal != null ? principal.getName() : "NULL");
    }

    @GetMapping("/oauth2")
    public ModelAndView oauth2Page() {
        ModelAndView mav                   = new ModelAndView("auth/oauth2-page");
        String       authorizationEndpoint = properties.getOAuth2Security().getAuthorizationEndpoint();
        String       github                = authorizationEndpoint + "/github";
        String       google                = authorizationEndpoint + "/google";

        mav.addObject("security", properties);
        mav.addObject("github", github);
        mav.addObject("google", google);

        return mav;
    }

    @GetMapping("/email")
    public ModelAndView emailPage() {
        ModelAndView mav = new ModelAndView("auth/email-page");

        mav.addObject("security", properties);

        return mav;
    }

}
