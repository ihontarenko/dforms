package df.base.jpa;

import df.base.common.hibernate5.generator.PrefixedId;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "SECURITY_PRIVILEGES")
public class Privilege {

    @Id
    @Column(name = "ID")
    @PrefixedId(
            prefixValue = "UPE",
            sequenceName = "PRIVILEGE",
            initialValue = 100,
            incrementBy = 10
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
