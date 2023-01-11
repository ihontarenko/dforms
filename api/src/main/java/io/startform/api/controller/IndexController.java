package io.startform.api.controller;

import io.startform.parent.jpa.geo.Country;
import io.startform.parent.jpa.geo.CountryRepository;
import io.startform.parent.service.HelloService;
import io.startform.parent.service.StateCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/")
public class IndexController {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private StateCheck stateCheck;

    @Autowired
    private Environment env;

    @Autowired
    private CountryRepository repository;

    private final HelloService helloService;

    public IndexController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    public String hello() {
        return getClass().getName() + " " + helloService.hello();
    }

    @GetMapping("/security/login")
    public String login() {
        System.out.println("from api");
        return env.getProperty("pebble.suffix");
    }

    @GetMapping("/country/{iso}")
    public Country getCountry(@PathVariable("iso") String iso) {
        return repository.getFirst("iso", iso);
    }

    @GetMapping("/object")
    public String object() {
        String value = String.valueOf(stateCheck.hashCode());

        value += " > " + stateCheck.getValue();

        stateCheck.setValue("from API");

        return value;
    }

    @GetMapping("/beans")
    public String beans() {
        return String.join("\n", Arrays.asList(applicationContext.getBeanDefinitionNames()));
    }



}
