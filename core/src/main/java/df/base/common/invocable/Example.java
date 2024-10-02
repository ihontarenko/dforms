package df.base.common.invocable;

import df.base.common.libs.jbm.StringUtils;

public class Example {

    public static void main(String[] args) {
        Invocable invocable = new StaticMethod(StringUtils.class, "underscored", String.class, boolean.class);

        invocable.addParameter(new MethodParameter(0, "test"));
        invocable.addParameter(new MethodParameter(1, true));

        System.out.println(invocable.invoke());
    }

}
