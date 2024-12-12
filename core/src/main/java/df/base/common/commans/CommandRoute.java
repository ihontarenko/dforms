package df.base.common.commans;

public record CommandRoute(String command, String action) {

    public String string() {
        return "%s/%s".formatted(command, action);
    }

}
