package df.base.common.libs.jbm.scanner;

public interface CompositeScanner<T> {

    default void addScanner(Scanner<T> scanner) {
        throw new UnsupportedOperationException(Scanner.UNSUPPORTED_FOR_CLASS_MESSAGE + this.getClass().getName());
    }

}
