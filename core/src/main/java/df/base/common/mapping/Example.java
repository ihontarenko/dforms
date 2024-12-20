package df.base.common.mapping;

import df.base.common.matcher.reflection.ClassMatchers;

public class Example {

    public static void main(String... arguments) {
        Mapping mapping = MappingFactory.create();

        System.out.println(
                ClassMatchers.isJavaPackage().matches(String.class)
        );

        System.out.println(
                mapping.map(mapping).toString()
        );


    }

}
