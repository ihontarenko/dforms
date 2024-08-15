package df.base.model.user;

import df.base.jpa.Privilege;
import df.base.validation.Unique;

@Unique.Set({
        @Unique(
                target = "name",
                fields = {@Unique.Field(objectName = "name", entityName = "name")},
                entityClass = Privilege.class,
                message = "privilege name already taken",
                existence = "id"
        ),
        @Unique(
                target = "id",
                fields = {@Unique.Field(objectName = "id", entityName = "id")},
                entityClass = Privilege.class,
                reverse = true,
                unique = false,
                message = "the PRIVILEGE_ID must exist",
                existence = "id"
        )
})
public class PrivilegeDTO {

    private String id;
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
