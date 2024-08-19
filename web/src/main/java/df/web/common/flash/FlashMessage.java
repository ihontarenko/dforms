package df.web.common.flash;

public class FlashMessage {

    private final String message;
    private final Type   type;

    public FlashMessage(String message, Type type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public Type getType() {
        return type;
    }

    public static FlashMessage create(String message, Type type) {
        return new FlashMessage(message, type);
    }

    public static FlashMessage success(String message) {
        return new FlashMessage(message, Type.SUCCESS);
    }

    public static FlashMessage primary(String message) {
        return new FlashMessage(message, Type.OK);
    }

    public static FlashMessage dark(String message) {
        return new FlashMessage(message, Type.DARK);
    }

    public static FlashMessage warning(String message) {
        return new FlashMessage(message, Type.WARNING);
    }

    public static FlashMessage error(String message) {
        return new FlashMessage(message, Type.ERROR);
    }

    public enum Type {

        SUCCESS("alert-success"),
        ERROR("alert-danger"),
        WARNING("alert-warning"),
        NOTICE("alert-info"),
        OK("alert-primary"),
        SECONDARY("alert-secondary"),
        LIGHT("alert-light"),
        DARK("alert-dark");

        private final String bootstrapClass;

        Type(String bootstrapClass) {
            this.bootstrapClass = bootstrapClass;
        }

        public String getBootstrapClass() {
            return bootstrapClass;
        }

    }

}
