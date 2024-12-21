package df.base.common.mapping;

import df.base.common.matcher.reflection.ClassMatchers;
import df.base.html.bean_console.MethodSetBuilder;

public class Example {

    public static void main(String... arguments) {
        Mapping mapping = MappingFactory.create();
        System.out.println(
                mapping.map(MethodSetBuilder.class).toString()
        );


    }

}
