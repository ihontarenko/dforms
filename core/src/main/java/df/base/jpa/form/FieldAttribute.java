package df.base.jpa.form;

import df.base.internal.hibernate.generator.PrefixedId;
import jakarta.persistence.*;

@Entity
@Table(name = "DF_FORM_FIELD_ATTRIBUTES")
public class FieldAttribute {

    @Id
    @PrefixedId(prefixValue = "FFA", sequenceName = "FORM_FIELD_ATTRIBUTE")
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "FIELD_ID", nullable = false)
    private Field formField;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "VALUE", nullable = false)
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Field getFormField() {
        return formField;
    }

    public void setFormField(Field formField) {
        this.formField = formField;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

