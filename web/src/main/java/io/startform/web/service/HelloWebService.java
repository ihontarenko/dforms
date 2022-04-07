package io.startform.web.service;

import io.startform.parent.service.HelloService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class HelloWebService implements HelloService {

    private final Environment environment;

    public HelloWebService(Environment environment) {
        this.environment = environment;
    }

    public String hello() {
        return environment.getProperty("hello");
    }

}
