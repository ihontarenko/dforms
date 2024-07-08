package df.parent.library.structure;

public class Pair<A, B> {

    private A valueA;
    private B valueB;

    private Pair(A a, B b) {
        valueA = a;
        valueB = b;
    }

    public static <A, B> Pair<A, B> of(A a, B b) {
        return new Pair<>(a, b);
    }

    public A getValueA() {
        return valueA;
    }

    public B getValueB() {
        return valueB;
    }

}
