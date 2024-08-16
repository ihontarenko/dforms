package df.base.jpa;

import df.base.common.hibernate.generator.IdPrefixGenerator;
import df.base.common.hibernate.generator.PrefixedId;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SECURITY_ROLES")
public class Role {

    @Id
    @Column(name = "ID")
    @PrefixedId(
            prefixValue = "RLE",
            sequenceName = "ROLE",
            initialValue = 1000,
            incrementBy = 1,
            prefixGenerator = DefaultIdGenerator.class,
            numberFormat = "%04d"
    )
    private String id;

    @Column(name = "ROLE")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "SECURITY_ROLES_PRIVILEGES",
            inverseJoinColumns = @JoinColumn(name = "PRIVILEGE_ID"),
            joinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private Set<Privilege> privileges = new HashSet<>();

    @ManyToMany(
            mappedBy = "roles",
            fetch = FetchType.LAZY
    )
    private Set<User> users = new HashSet<>();

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

    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public void addPrivilege(Privilege privilege) {
        privileges.add(privilege);
    }

    public void removePrivilege(Privilege privilege) {
        privileges.remove(privilege);
    }

    public void removeAllPrivilege() {
        privileges.clear();
    }

    public Set<User> getUsers() {
        return users;
    }

    public static class IdGenerator implements IdPrefixGenerator {

        @Override
        public String generate(Object ordinalID, PrefixedId annotation, Object entity) {
            return "%s%s%s%04x".formatted(annotation.prefixValue(), annotation.prefixSeparator(),
                    annotation.numberFormat().formatted(ordinalID), entity.hashCode()).toUpperCase();
        }

    }

}
