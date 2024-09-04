package df.base.common.spring.jpa.entity_graph;

public class EntityGraphException extends RuntimeException {

    public EntityGraphException(String message) {
        super(message);
    }

    public EntityGraphException(Throwable cause) {
        super(cause);
    }

    public EntityGraphException(String message, Throwable cause) {
        super(message, cause);
    }

}
