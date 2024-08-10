package df.base.jpa.forms;

import df.base.common.hibernate5.generator.PrefixedId;
import jakarta.persistence.*;

@Entity
@Table(name = "DF_FORM_FIELD_CONFIG")
public class FormFieldConfig {

    @Id
    @PrefixedId(prefixValue = "FFC", sequenceName = "FORM_FIELD_CONFIG")
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "FORM_FIELD_ID", nullable = false)
    private FormField formField;

    @Column(name = "CONFIG_NAME", nullable = false)
    private String configName;

    @Column(name = "CONFIG_VALUE", nullable = false)
    private String configValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FormField getFormField() {
        return formField;
    }

    public void setFormField(FormField formField) {
        this.formField = formField;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
}

