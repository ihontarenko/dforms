package pro.javadev.test.sub.application1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    
    private String name;

    @Autowired
    public IndexController(String name) {
        this.name = name;
    }

    public String index() {
        return name;
    }
    
}
