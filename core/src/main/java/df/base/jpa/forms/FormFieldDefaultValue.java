package df.base.jpa.forms;

import df.base.common.hibernate5.generator.PrefixedId;
import jakarta.persistence.*;

@Entity
@Table(name = "DF_FORM_FIELD_DEFAULT_VALUES")
public class FormFieldDefaultValue {

    @Id
    @PrefixedId(prefixValue = "FDV", sequenceName = "FORM_FIELD_DEFAULT_VALUE")
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "FORM_ID", nullable = false)
    private Form form;

    @ManyToOne
    @JoinColumn(name = "FIELD_ID", nullable = false)
    private FormField formField;

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

    public FormField getFormField() {
        return formField;
    }

    public void setFormField(FormField formField) {
        this.formField = formField;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}

