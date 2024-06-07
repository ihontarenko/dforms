package io.startform.web.controller;

import io.startform.parent.jpa.UserRepository;
import io.startform.parent.library.i18n.Translator;
import io.startform.web.property.HttpSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/security")
public class CsrfTokenController {

    @Autowired
    private Translator translator;

    @Autowired
    private UserRepository repository;

    @GetMapping(value = "/csrf", produces = {MediaType.TEXT_PLAIN_VALUE})
    @ResponseBody
    public String getCsrfToken(HttpServletRequest request) {
        return ((CsrfToken) request.getAttribute(CsrfToken.class.getName())).getToken();
    }

}