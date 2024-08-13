package df.base.jpa.form;

import df.base.common.hibernate5.generator.PrefixedId;
import jakarta.persistence.*;

@Entity
@Table(name = "DF_FORM_FIELD_ENTRIES")
public class FormFieldEntry {

    @Id
    @PrefixedId(prefixValue = "FFE", sequenceName = "FORM_FIELD_ENTRY")
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "FORM_ENTRY_ID", nullable = false)
    private FormEntry formEntry;

    @ManyToOne
    @JoinColumn(name = "FORM_FIELD_ID", nullable = false)
    private FormField formField;

    @Column(name = "VALUE", nullable = false)
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FormEntry getFormEntry() {
        return formEntry;
    }

    public void setFormEntry(FormEntry formEntry) {
        this.formEntry = formEntry;
    }

    public FormField getFormField() {
        return formField;
    }

    public void setFormField(FormField formField) {
        this.formField = formField;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

