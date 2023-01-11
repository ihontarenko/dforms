package io.startform.api.service;

import io.startform.parent.service.HelloService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class HelloAPIService implements HelloService {

    private final Environment environment;

    public HelloAPIService(Environment environment) {
        this.environment = environment;
    }

    public String hello() {
        return environment.getProperty("hello");
    }

}
