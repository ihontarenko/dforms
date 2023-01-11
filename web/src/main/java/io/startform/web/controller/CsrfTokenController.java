package io.startform.web.controller;

import io.startform.parent.jpa.UserRepository;
import io.startform.parent.jpa.geo.CountryRepository;
import io.startform.parent.library.hibernate5.naming.EnhancementPhysicalNamingStrategy;
import io.startform.parent.library.i18n.Translator;
import io.startform.web.property.HttpSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/security")
public class CsrfTokenController {

    @Autowired
    private Translator translator;

    @Autowired
    private UserRepository repository;

    @Autowired
    private CountryRepository countries;

    @GetMapping(value = "/csrf", produces = {MediaType.TEXT_PLAIN_VALUE})
    @ResponseBody
    public String getCsrfToken(HttpServletRequest request) {
        return ((CsrfToken) request.getAttribute(CsrfToken.class.getName())).getToken();
    }

    @GetMapping(value = "/text", produces = {MediaType.TEXT_PLAIN_VALUE})
    @ResponseBody
    public String getText(HttpSecurityProperties properties) {

        System.out.println(
                properties.getSessionCookie()
        );

        return translator.getMessage(
                "application.title"
        ) + countries.getFirst("iso", "UA").getCanonicalName();
    }

}