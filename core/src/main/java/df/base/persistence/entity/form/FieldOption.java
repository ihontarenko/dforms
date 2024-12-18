package df.base.persistence.entity.form;

import df.base.common.extensions.hibernate.generator.PrefixedId;
import df.base.persistence.entity.EntityNameAware;
import df.base.persistence.generator.NamedEntityIdGenerator;
import df.base.persistence.support.EntityConstants;
import jakarta.persistence.*;

@Entity
@Table(name = EntityConstants.TABLE_FIELD_OPTIONS)
public class FieldOption implements EntityNameAware {

    @Id
    @PrefixedId(
            prefixValue = "FLO",
            sequenceName = "FIELD_OPTION",
            prefixGenerator = NamedEntityIdGenerator.class,
            numberFormat = "%06d",
            initialValue = 1000,
            incrementBy = 10,
            prefixSeparator = "_"
    )
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

    @Override
    public String getName() {
        return getOptionValue();
    }
}

