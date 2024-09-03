package df.base.jpa.form;

import df.base.internal.hibernate.generator.PrefixedId;
import df.base.jpa.DefaultIdGenerator;
import jakarta.persistence.*;

@Entity
@Table(name = "DF_FORM_FIELD_GROUP")
public class FormFieldGroup {

    @Id
    @PrefixedId(
            prefixValue = "GRP",
            sequenceName = "FORM_FIELD_GROUP",
            prefixGenerator = DefaultIdGenerator.class,
            numberFormat = "%06d",
            initialValue = 100000,
            incrementBy = 1
    )
    @Column(name = "ID")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_FIELD_ID", referencedColumnName = "ID")
    private FormField parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FIELD_ID")
    private FormField field;

    private Integer sequenceOrder;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FormField getParent() {
        return parent;
    }

    public void setParent(FormField parentField) {
        this.parent = parentField;
    }

    public FormField getField() {
        return field;
    }

    public void setField(FormField field) {
        this.field = field;
    }

    public Integer getSequenceOrder() {
        return sequenceOrder;
    }

    public void setSequenceOrder(Integer sequenceOrder) {
        this.sequenceOrder = sequenceOrder;
    }
}
