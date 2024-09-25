package df.base.common.proxy;

public class Example {

    public static void main(String[] args) {
        User         user    = new User();
        ProxyFactory factory = new ProxyFactory(user);

        factory.addInterceptor(new LoggingInterceptor());

        UserInterface proxyUser = factory.getProxy();

        System.out.println(proxyUser.getName());;
    }

}
