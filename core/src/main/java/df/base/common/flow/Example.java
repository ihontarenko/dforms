package df.base.common.flow;

import java.util.Arrays;
import java.util.List;

public class Example {

    record User(String name) {}

    public static void main(String[] args) {
        List<String> words = Arrays.asList("Java", "Flow", "API", "Oh", "Framework", "Noo", "JavaDev");
        Flow<String> flow  = Flow.of(words);
        Flow<User> userFlow = Flow.of(Arrays.asList(new User("John"), new User("James"), new User("Homer"), new User("Rick"), new User("Morty")));

        System.out.println(
                Flow.of(
                        userFlow.groupBy(user -> user.name().charAt(0)).values()
                ).map(List::size).toList()
        );

        System.out.println(
                userFlow.map(User::name).map(String::toUpperCase).join("/", "[", "]")
        );

    }

}
