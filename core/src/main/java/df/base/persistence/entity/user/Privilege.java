package df.base.jpa;

import df.base.common.hibernate.generator.PrefixedId;
import jakarta.persistence.*;

@Entity
@Table(name = "SECURITY_PRIVILEGES")
public class Privilege implements EntityNameAware {

    @Id
    @Column(name = "ID")
    @PrefixedId(
            prefixValue = "P",
            sequenceName = "PRIVILEGE",
            prefixGenerator = NamedEntityIdGenerator.class,
            numberFormat = "%04d",
            initialValue = 1000,
            incrementBy = 1,
            prefixSeparator = "_"
    )
    private String id;

    @Column(name = "PRIVILEGE")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
