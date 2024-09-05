package df.base.mapping;

import df.base.common.Mapper;
import df.base.mapping.form.FieldMapper;
import df.base.mapping.form.FormConfigMapper;
import df.base.mapping.form.FieldConfigMapper;
import df.base.mapping.form.FormMapper;
import df.base.mapping.user.PrivilegeMapper;
import df.base.mapping.user.RoleMapper;
import df.base.mapping.user.UserMapper;
import df.base.persistence.entity.form.Field;
import df.base.persistence.entity.form.FieldConfig;
import df.base.persistence.entity.form.Form;
import df.base.persistence.entity.form.FormConfig;
import df.base.persistence.entity.user.Privilege;
import df.base.persistence.entity.user.Role;
import df.base.persistence.entity.user.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

public enum Mappers {

    INSTANCE;

    private static final Map<Class<?>, Mapper<?, ?>> MAPPER_MAP;

    static {
        MAPPER_MAP = new HashMap<>();
        // user entities
        MAPPER_MAP.put(User.class, new UserMapper());
        MAPPER_MAP.put(Role.class, new RoleMapper());
        MAPPER_MAP.put(Privilege.class, new PrivilegeMapper());
        // form entities
        MAPPER_MAP.put(Form.class, new FormMapper());
        MAPPER_MAP.put(FormConfig.class, new FormConfigMapper());
        MAPPER_MAP.put(Field.class, new FieldMapper());
        MAPPER_MAP.put(FieldConfig.class, new FieldConfigMapper());
    }

    public <S, D> Mapper<S, D> get(Class<S> classType) {
        Mapper<?, ?> mapper = MAPPER_MAP.get(classType);

        if (mapper == null) {
            throw new MapperException("No registered mapper for source-type '%s'".formatted(classType.getName()));
        }

        return (Mapper<S, D>) mapper;
    }

}
