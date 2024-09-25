package df.base.common.proxy;

public class User implements UserInterface{

    private final String name = "John";

    public String getName() {
        return name;
    }
}
