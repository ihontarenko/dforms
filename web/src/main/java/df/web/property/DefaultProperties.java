package df.web.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class DefaultProperties {

    private String hello;

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }

    @Override
    public String toString() {
        return "DefaultProperties{hello='%s'}".formatted(hello);
    }
}
