package df.base.common.matcher;

import df.base.common.validation.jakarta.EnumPatternValidator;

import java.lang.reflect.Method;

import static df.base.common.matcher.ClassMatchers.hasFieldName;
import static df.base.common.matcher.ClassMatchers.isPublic;
import static df.base.common.matcher.MatchContext.createDefault;
import static df.base.common.matcher.TextMatchers.contains;

public class Example {

    public static void main(String[] args) {

        Matcher<Class<?>> classMatcher = isPublic().and(hasFieldName(contains("atte")));
        MatchContext      context      = createDefault();

        boolean isMatch = classMatcher.matches(EnumPatternValidator.class, context);

        System.out.println("Class match: " + isMatch);
    }
}

