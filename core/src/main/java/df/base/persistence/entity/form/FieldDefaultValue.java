package df.base.persistence.entity.form;

import df.base.common.extensions.hibernate.generator.PrefixedId;
import df.base.persistence.support.EntityConstants;
import jakarta.persistence.*;

@Entity
@Table(name = EntityConstants.TABLE_FIELD_DEFAULT_VALUES)
public class FieldDefaultValue {

    @Id
    @PrefixedId(prefixValue = "FDV", sequenceName = "FORM_FIELD_DEFAULT_VALUE")
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "FORM_ID", nullable = false)
    private Form form;

    @ManyToOne
    @JoinColumn(name = "FIELD_ID", nullable = false)
    private Field field;

    @Column(name = "DEFAULT_VALUE", nullable = false)
    private String defaultValue;

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

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}

