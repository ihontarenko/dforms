package df.base.common.libs.confio;

public class ConfigContext {

    private String section;

    public ConfigContext(String section) {
        this.section = section;
    }

    public String getSection() {
        return section;
    }

}
