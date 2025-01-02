package df.base.common.container;

public enum TypeMapper {

    INT(int.class, Integer.class),
    LONG(long.class, Long.class),
    DOUBLE(double.class, Double.class),
    FLOAT(float.class, Float.class),
    BOOLEAN(boolean.class, Boolean.class),
    CHAR(char.class, Character.class),
    BYTE(byte.class, Byte.class),
    SHORT(short.class, Short.class),
    VOID(void.class, Void.class);

    private final Class<?> primitiveType;
    private final Class<?> wrapperType;

    TypeMapper(Class<?> primitiveType, Class<?> wrapperType) {
        this.primitiveType = primitiveType;
        this.wrapperType = wrapperType;
    }

    public Class<?> getPrimitiveType() {
        return primitiveType;
    }

    public Class<?> getWrapperType() {
        return wrapperType;
    }

    public static Class<?> getWrapperFor(Class<?> primitiveType) {
        for (TypeMapper mapper : values()) {
            if (mapper.primitiveType.equals(primitiveType)) {
                return mapper.wrapperType;
            }
        }

        throw new IllegalArgumentException("Unknown primitive type: " + primitiveType);
    }

    public static Class<?> getPrimitiveFor(Class<?> wrapperType) {
        for (TypeMapper mapper : values()) {
            if (mapper.wrapperType.equals(wrapperType)) {
                return mapper.primitiveType;
            }
        }

        throw new IllegalArgumentException("Unknown wrapper type: " + wrapperType);
    }

    public static boolean isPrimitive(Class<?> type) {
        for (TypeMapper mapper : values()) {
            if (mapper.primitiveType.equals(type)) {
                return true;
            }
        }
        return false;
    }

}
