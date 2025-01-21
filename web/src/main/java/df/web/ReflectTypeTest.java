package df.web;

import com.sun.jdi.CharType;
import org.springframework.core.ResolvableType;
import svit.reflection.ReflectType;

import java.lang.reflect.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ReflectTypeTest {

    public static void main(String[] args) {
        // Example 1: GenericArrayType

        try {
            Field       f = TestClass.class.getDeclaredField("genericComplex");
            ReflectType genericArrayType  = ReflectType.forField(f);

//            ResolvableType resolvableType = ResolvableType.forField(f);

            ReflectType type = genericArrayType.navigate(IFACE_X.class);

//            ResolvableType aaa2 = resolvableType.getInterfaces()[0].getInterfaces()[2].getGeneric(0);
            ReflectType aaa = type.getGeneric(0);

            System.out.println(aaa);
            System.out.println(aaa.hashCode());

            System.out.println("GenericArrayType: " + genericArrayType);
        } catch (NoSuchFieldException e) {
            System.err.println("Error: Field 'genericArray' not found.");
        }

        // Example 2: ParameterizedType
       /* try {
            Field parameterizedField = TestClass.class.getDeclaredField("map");
            ReflectType parameterizedType = ReflectType.forField(parameterizedField);
            System.out.println("ParameterizedType: " + parameterizedType);

            // Accessing generics
            ReflectType[] generics = parameterizedType.getGenerics();
            System.out.println("Generics of Map: ");
            for (ReflectType generic : generics) {
                System.out.println("  - " + generic);
            }
        } catch (NoSuchFieldException e) {
            System.err.println("Error: Field 'map' not found.");
        }

        // Example 3: WildcardType
        try {
            Field wildcardField = TestClass.class.getDeclaredField("listWithWildcardExtends");
            ReflectType wildcardType = ReflectType.forField(wildcardField);
            System.out.println("WildcardType: " + wildcardType);
        } catch (NoSuchFieldException e) {
            System.err.println("Error: Field 'listWithWildcard' not found.");
        }

        // Example 4: Raw Type
        try {
            Field rawField = TestClass.class.getDeclaredField("rawList");
            ReflectType rawType = ReflectType.forField(rawField);
            System.out.println("RawType: " + rawType);
        } catch (NoSuchFieldException e) {
            System.err.println("Error: Field 'rawList' not found.");
        }

        // Example 5: Primitive Type
        try {
            Field primitiveField = TestClass.class.getDeclaredField("primitiveInt");
            ReflectType primitiveType = ReflectType.forField(primitiveField);
            System.out.println("PrimitiveType: " + primitiveType);
        } catch (NoSuchFieldException e) {
            System.err.println("Error: Field 'primitiveInt' not found.");
        }*/
    }

    static class GlobalService<D> implements IFACE_X<D> {
        @Override
        public D get() {
            return null;
        }
    }

    interface IFACE_X<S> extends IFACE_C<S> {}

    interface IFACE_C<X> extends IFACE_B<X> {}

    interface IFACE_B<A> extends IFACE_A<A> {}

    interface IFACE_A<V> {
        V get();
    }

    static class TestClass {
        public GlobalService<String[]> global; // long inheritance
        public RootUserClass<String, Integer[], CharType> genericComplex; // GenericArrayType
        public RootUserClass<String, Integer[], CharType>[] genericArrayComplex; // GenericArrayType
        public List<String>[]                               genericArray; // GenericArrayType
        public Map<String, List<Integer>> map; // ParameterizedType
        public List<? extends Number> listWithWildcardExtends; // WildcardType
        public List<? super Number>   listWithWildcardSuper; // WildcardType
        public List                   rawList; // Raw Type
        public int primitiveInt; // Primitive Type
    }

    abstract static class RootUserClass<T, V, X> implements Iface<T, V, Set<X>> {

    }

    interface Iface<T, V, X> extends List<V>, Comparable<T>, IFACE_X<X> {}

}
