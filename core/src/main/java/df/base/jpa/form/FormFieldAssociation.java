package df.base.jpa.form;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "FORM_FIELD_ASSOCIATION")
public class FormFieldAssociation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_FIELD_ID", nullable = false)
    private FormField parentField;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FIELD_ID", nullable = false)
    private FormField field;

    private Integer sequenceOrder;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FormField getParentField() {
        return parentField;
    }

    public void setParentField(FormField parentField) {
        this.parentField = parentField;
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
