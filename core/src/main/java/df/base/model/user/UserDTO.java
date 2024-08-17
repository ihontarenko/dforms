package df.base.model.user;

import df.base.jpa.User;
import df.base.security.Provider;
import df.base.common.jpa.FieldSet;
import df.base.validation.constrain.ResourceExistence;
import df.base.validation.constrain.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

import static df.base.common.jpa.FieldSet.Comparison.NOT_EQUAL;

@ResourceExistence.Set({
        // validate an email for new users
        @ResourceExistence(
                target = "email",
                fields = {@FieldSet(objectName = "email", entityName = "email")},
                entityClass = User.class,
                message = "user with this email already registered (new)",
                existence = "id"
        ),
        // validate for updating existing user
        @ResourceExistence(
                target = "email",
                fields = {
                        @FieldSet(objectName = "id", entityName = "id", comparison = NOT_EQUAL),
                        @FieldSet(objectName = "email", entityName = "email")
                },
                entityClass = User.class,
                reverse = true,
                message = "user with this email already registered (upd)",
                existence = "id"
        )
})
public class UserDTO {

    public static final String OAUTH2_USER_ROLE = "ROLE_OAUTH2_USER";

    private String id;

    @NotEmpty(message = "user email is required")
    @Email
    @Size(max = 32)
    private String email;

    @NotEmpty(message = "user name is required")
    @Size(max = 32)
    private String name;

    private Provider provider = Provider.LOCAL;

    @StrongPassword
    private String password;

    private boolean enabled;

    @NotEmpty(message = "select at least one role")
    private List<String> roles = new ArrayList<>();

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
