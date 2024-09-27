package df.base.common.parser;

import java.util.Random;

public class TestService {

    public String getValue(int value, String name) {
        return getClass().getName() + value + name;
    }

    public static int random() {
        return new Random().nextInt();
    }

    public String hello(int random, String var, double value) {
        return "%s - %s - %s".formatted(random, var, value);
    }

}
