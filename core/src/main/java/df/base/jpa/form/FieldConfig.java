package df.base.jpa.form;

import df.base.internal.hibernate.generator.PrefixedId;
import df.base.jpa.EntityConstants;
import df.base.jpa.EntityGraphConstants;
import df.base.jpa.EntityNameAware;
import df.base.jpa.NamedEntityIdGenerator;
import jakarta.persistence.*;

@Entity
@Table(name = EntityConstants.TABLE_FIELD_CONFIG)
@NamedEntityGraph(
        name = EntityGraphConstants.FORM_FIELD_CONFIG_WITH_FIELD,
        attributeNodes = @NamedAttributeNode("field")
)
public class FieldConfig implements EntityNameAware {

    @Id
    @PrefixedId(
            prefixValue = "cfg",
            sequenceName = "FORM_FIELD_CONFIG",
            prefixGenerator = NamedEntityIdGenerator.class,
            numberFormat = "%06d",
            incrementBy = 1,
            prefixSeparator = "_"
    )
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "FIELD_ID", nullable = false)
    private Field field;

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

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
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

