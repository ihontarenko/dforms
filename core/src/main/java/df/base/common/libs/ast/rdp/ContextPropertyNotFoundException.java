package df.base.common.libs.ast.rdp;

public class ContextPropertyNotFoundException extends RuntimeException {
    public ContextPropertyNotFoundException() {
        super();
    }

    public ContextPropertyNotFoundException(String message) {
        super(message);
    }

    public ContextPropertyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
