package df.base.jpa.form;

import df.base.common.hibernate.generator.PrefixedId;
import df.base.jpa.EntityGraphConstants;
import df.base.jpa.EntityNameAware;
import df.base.jpa.NamedEntityIdGenerator;
import jakarta.persistence.*;

@Entity
@Table(name = "DF_FORM_CONFIG")
@NamedEntityGraph(
        name = EntityGraphConstants.FORM_CONFIG_WITH_FORM,
        attributeNodes = @NamedAttributeNode("form")
)
public class FormConfig implements EntityNameAware {

    @Id
    @PrefixedId(
            prefixValue = "CFG",
            sequenceName = "FORM_CONFIG",
            prefixGenerator = NamedEntityIdGenerator.class,
            incrementBy = 1,
            numberFormat = "%d")
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "FORM_ID", nullable = false)
    private Form form;

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

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
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

    @Override
    public String getName() {
        return getConfigName();
    }
}
