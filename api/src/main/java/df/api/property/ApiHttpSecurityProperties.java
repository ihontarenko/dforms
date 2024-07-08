package df.api.property;

public class ApiHttpSecurityProperties {

    private String h2Console;

    public String getH2Console() {
        return h2Console;
    }

    public void setH2Console(String h2Console) {
        this.h2Console = h2Console;
    }

    @Override
    public String toString() {
        return "ApiHttpSecurityProperties{h2Console='%s'}".formatted(h2Console);
    }
}
