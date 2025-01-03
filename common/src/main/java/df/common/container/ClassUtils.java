package df.common.container;

abstract public class ClassUtils {

    public static final char PACKAGE_SEPARATOR = '.';

    public static String getShortName(Object clazz) {
        Class<?> classType = clazz.getClass();

        if (clazz instanceof Class<?> type) {
            classType = type;
        }

        return getShortName(classType);
    }

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
