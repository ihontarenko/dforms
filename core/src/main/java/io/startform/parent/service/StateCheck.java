package io.startform.parent.service;

import org.springframework.stereotype.Component;

@Component
public class StateCheck {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
