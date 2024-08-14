package df.base.model.user;

import df.base.jpa.User;
import df.base.security.Provider;
import df.base.validation.Unique;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Unique(
        target = "email",
        fields = {@Unique.Field(objectName = "email", entityName = "email")},
        entityClass = User.class,
        message = "user with this email already registered",
        existence = "id"
)
public class UserDTO {

    private String id;

    @NotEmpty(message = "user email is required")
    @Size(max = 32)
    private String email;

    @NotEmpty(message = "user name is required")
    @Size(max = 32)
    private String name;

    private Provider provider = Provider.LOCAL;

    private String password;

    private boolean enabled;

    @NotEmpty(message = "select at least one role")
    private List<String> roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
