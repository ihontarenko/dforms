package df.base.jpa;

import df.base.common.hibernate5.generator.IdPrefixGenerator;
import df.base.common.hibernate5.generator.PrefixedId;
import df.base.security.Provider;
import jakarta.persistence.*;
import org.hibernate.id.IdentifierGenerationException;

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
            initialValue = 100,
            incrementBy = 10,
            prefixGenerator = UserIdPrefixGenerator.class
    )
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "PROVIDER", columnDefinition = "VARCHAR")
    private Provider provider;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ENABLED")
    private boolean enabled;

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public static class UserIdPrefixGenerator implements IdPrefixGenerator {

        @Override
        public String generated(Object ordinalID, PrefixedId annotation, Object entity) {
            if (entity instanceof User user) {
                return "%s%s%s%s%s".formatted(
                        annotation.prefixValue(), annotation.prefixSeparator(), "%04x".formatted(user.hashCode()).toUpperCase(),
                        annotation.prefixSeparator(), annotation.numberFormat().formatted(ordinalID));
            }

            throw new IdentifierGenerationException("IdPrefixGenerator allow only %s entity but %s passed"
                    .formatted(User.class.getName(), entity.getClass().getName()));
        }

    }

}
