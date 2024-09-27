package df.base.persistence.entity.form;

import df.base.common.extensions.hibernate.generator.PrefixedId;
import df.base.persistence.entity.EntityNameAware;
import df.base.persistence.generator.NamedEntityIdGenerator;
import df.base.persistence.support.EntityConstants;
import jakarta.persistence.*;

@Entity
@Table(name = EntityConstants.TABLE_FIELD_ENTRIES)
public class FieldEntry implements EntityNameAware {

    @Id
    @PrefixedId(
            prefixValue = "E",
            sequenceName = "FORM_FIELD_ENTRY",
            prefixGenerator = NamedEntityIdGenerator.class,
            numberFormat = "%04d",
            initialValue = 1000,
            incrementBy = 1,
            prefixSeparator = "_"
    )
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

    @Override
    public String getName() {
        return getField().getName();
    }

}

