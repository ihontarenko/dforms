package df.base.common.libs.jbm;

abstract public class ClassUtils {

    public static final char PACKAGE_SEPARATOR = '.';

    public static String getShortName(Class<?> clazz) {
        String className = clazz.getName();

        if (!(clazz.isArray() || clazz.isPrimitive())) {
            int lastIndex = className.lastIndexOf(PACKAGE_SEPARATOR);
            if (lastIndex != -1) {
                className = className.substring(lastIndex + 1);
            }
        }

        return className;
    }

}
