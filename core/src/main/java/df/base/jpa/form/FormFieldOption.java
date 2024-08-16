package df.base.jpa.form;

import df.base.common.hibernate.generator.PrefixedId;
import jakarta.persistence.*;

@Entity
@Table(name = "DF_FORM_FIELD_OPTIONS")
public class FormFieldOption {

    @Id
    @PrefixedId(prefixValue = "FFO", sequenceName = "FORM_FIELD_OPTION")
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "FIELD_ID", nullable = false)
    private FormField formField;

    @Column(name = "OPTION_VALUE", nullable = false)
    private String optionValue;

    @Column(name = "OPTION_LABEL", nullable = false)
    private String optionLabel;

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

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public String getOptionLabel() {
        return optionLabel;
    }

    public void setOptionLabel(String optionLabel) {
        this.optionLabel = optionLabel;
    }
}

