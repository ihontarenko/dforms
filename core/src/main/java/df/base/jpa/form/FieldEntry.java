package df.base.jpa.form;

import df.base.internal.hibernate.generator.PrefixedId;
import df.base.jpa.EntityConstants;
import jakarta.persistence.*;

@Entity
@Table(name = EntityConstants.TABLE_FIELD_ENTRIES)
public class FieldEntry {

    @Id
    @PrefixedId(prefixValue = "E", sequenceName = "FORM_FIELD_ENTRY")
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "FORM_ENTRY_ID", nullable = false)
    private FormEntry formEntry;

    @ManyToOne
    @JoinColumn(name = "FIELD_ID", nullable = false)
    private Field field;

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

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

