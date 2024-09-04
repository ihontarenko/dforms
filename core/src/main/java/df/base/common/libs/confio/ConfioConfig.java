package df.base.common.libs.confio;

import java.util.*;

public class ConfioConfig {
    private Map<String, Object> config = new HashMap<>();

    public void put(String section, String key, Object value) {
        Map<String, Object> currentSection = getSection(section);
        if (currentSection == null) {
            currentSection = new HashMap<>();
            config.put(section, currentSection);
        }
        currentSection.put(key, value);
    }

    public Object getValue(ConfigContext context, String key) {
        Map<String, Object> section = getSection(context.getSection());
        if (section != null) {
            return section.get(key);
        }
        return null;
    }

    public Map<String, Object> getSection(String sectionName) {
        String[] parts = sectionName.split("\\.");
        Map<String, Object> currentSection = config;
        for (String part : parts) {
            currentSection = (Map<String, Object>) currentSection.get(part);
            if (currentSection == null) {
                return null;
            }
        }
        return currentSection;
    }

    public void putSection(String sectionName, Map<String, Object> section) {
        config.put(sectionName, section);
    }
}
