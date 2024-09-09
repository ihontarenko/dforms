package df.base.persistence.entity.form;

import df.base.common.extensions.hibernate.generator.PrefixedId;
import df.base.persistence.entity.EntityNameAware;
import df.base.persistence.generator.NamedEntityIdGenerator;
import df.base.persistence.support.EntityConstants;
import jakarta.persistence.*;

@Entity
@Table(name = EntityConstants.TABLE_FIELD_ATTRIBUTES)
public class FieldAttribute implements EntityNameAware {

    @Id
    @PrefixedId(
            prefixValue = "a",
            sequenceName = "FIELD_ATTRIBUTE",
            prefixGenerator = NamedEntityIdGenerator.class,
            numberFormat = "%04d",
            initialValue = 1000,
            incrementBy = 1,
            prefixSeparator = "_"
    )
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "FIELD_ID", nullable = false)
    private Field field;

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

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
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

