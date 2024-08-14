package df.base.jpa;

import df.base.common.hibernate5.generator.PrefixedId;
import df.base.security.Provider;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "SECURITY_USERS")
public class User {

    @Id
    @Column(name = "ID")
    @PrefixedId(
            prefixValue = "USR",
            sequenceName = "USER",
            initialValue = 1000,
            incrementBy = 1,
            prefixGenerator = DefaultIdGenerator.class,
            numberFormat = "%04d"
    )
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "PROVIDER", columnDefinition = "VARCHAR")
    private Provider provider;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ENABLED")
    private boolean enabled;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "SECURITY_USERS_ROLES",
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"),
            joinColumns = @JoinColumn(name = "USER_ID")
    )
    private Set<Role> roles = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isLocal() {
        return provider == Provider.LOCAL;
    }

    public boolean isOAuth2() {
        return !isLocal();
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }

    @Override
    public boolean equals(Object that) {
        if(this == that) {
            return true;
        }

        if(that == null || this.getClass() != that.getClass()) {
            return false;
        }

        boolean[] equals = new boolean[] {
                Objects.equals(((User)that).id, this.id),
                Objects.equals(((User)that).provider, this.provider),
                Objects.equals(((User)that).email, this.email),
        };

        boolean isEqual = true;

        for (boolean equal : equals) {
            isEqual = isEqual && equal;
        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, provider, email);
    }

}
