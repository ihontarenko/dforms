package df.web.common.flash;

public enum FlashMessageType {

    SUCCESS("alert-success"),
    ERROR("alert-danger"),
    WARNING("alert-warning"),
    NOTICE("alert-info"),
    OK("alert-primary"),
    SECONDARY("alert-secondary"),
    LIGHT("alert-light"),
    DARK("alert-dark");

    private final String bootstrapClass;

    FlashMessageType(String bootstrapClass) {
        this.bootstrapClass = bootstrapClass;
    }

    public String getBootstrapClass() {
        return bootstrapClass;
    }

}
