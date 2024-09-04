package df.base.jpa.form;

import df.base.common.hibernate.generator.PrefixedId;
import df.base.jpa.EntityConstants;
import jakarta.persistence.*;

@Entity
@Table(name = EntityConstants.TABLE_FIELD_OPTIONS)
public class FieldOption {

    @Id
    @PrefixedId(prefixValue = "o", sequenceName = "FORM_FIELD_OPTION")
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "FIELD_ID", nullable = false)
    private Field field;

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

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
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

