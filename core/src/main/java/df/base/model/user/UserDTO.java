package df.base.model.user;

import df.base.internal.support.jpa.JpaCriteria;
import df.base.jpa.User;
import df.base.security.Provider;
import df.base.validation.hibernate.Fields;
import df.base.validation.hibernate.constraint.JpaResource;
import df.base.validation.hibernate.constraint.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

import static df.base.validation.hibernate.Fields.ValueType.FIELD_NAME;

@JpaResource.List({
        // validate an email for new users
        @JpaResource(
                pointer = "email",
                fields = {
                        @Fields(
                                objectValue = @Fields.Value(value = "email", type = FIELD_NAME),
                                entityField = "email")
                },
                entityClass = User.class,
                message = "[NEW]: user with this email already registered",
                applier = "!#hasText(id)"
        ),
        // validate for updating existing user
        @JpaResource(
                pointer = "email",
                fields = {
                        @Fields(
                                objectValue = @Fields.Value(value = "id", type = FIELD_NAME),
                                entityField = "id",
                                comparison = JpaCriteria.Comparison.NOT_EQUAL),
                        @Fields(
                                objectValue = @Fields.Value(value = "email", type = FIELD_NAME),
                                entityField = "email")
                },
                entityClass = User.class,
                message = "[UPD]: user with this email already registered",
                applier = "#hasText(id)"
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
