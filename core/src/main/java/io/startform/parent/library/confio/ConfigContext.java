package io.startform.parent.library.confio;

public class ConfigContext {

    private String section;

    public ConfigContext(String section) {
        this.section = section;
    }

    public String getSection() {
        return section;
    }

}
