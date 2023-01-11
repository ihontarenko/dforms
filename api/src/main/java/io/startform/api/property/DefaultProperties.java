package io.startform.api.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class DefaultProperties {

    private String hello;
    private ApiHttpSecurityProperties httpSecurity;

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }

    public ApiHttpSecurityProperties getHttpSecurity() {
        return httpSecurity;
    }

    public void setHttpSecurity(ApiHttpSecurityProperties httpSecurity) {
        this.httpSecurity = httpSecurity;
    }

    @Override
    public String toString() {
        return "DefaultProperties{hello='%s', httpSecurity=%s}".formatted(hello, httpSecurity);
    }
}
