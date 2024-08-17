package df.base.common.application_context.scanner.filter.type;

import java.lang.reflect.Modifier;

public class AccessModifierClassFilter implements TypeFilter {

    private final int     modifiers;
    private final boolean invert;

    public AccessModifierClassFilter(int modifiers, boolean invert) {
        this.modifiers = modifiers;
        this.invert = invert;
    }

    public AccessModifierClassFilter(int modifiers) {
        this(modifiers, false);
    }

    @Override
    public boolean accept(Class<?> object) {
        boolean skipInterface = !object.isInterface() && ((modifiers & Modifier.ABSTRACT) == 0);
        boolean isApplicable  = (object.getModifiers() & modifiers) == 0;

        return invert == (isApplicable && skipInterface);
    }

}
