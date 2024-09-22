package df.base.mapping;

import df.base.common.mapping.Mapper;
import df.base.dto.form.*;
import df.base.dto.user.PrivilegeDTO;
import df.base.dto.user.RoleDTO;
import df.base.dto.user.UserDTO;
import df.base.mapping.form.*;
import df.base.mapping.user.PrivilegeMapper;
import df.base.mapping.user.RoleMapper;
import df.base.mapping.user.UserMapper;
import df.base.persistence.entity.form.*;
import df.base.persistence.entity.user.Privilege;
import df.base.persistence.entity.user.Role;
import df.base.persistence.entity.user.User;

import java.util.HashMap;
import java.util.Map;

public enum Mappers {

    INSTANCE;

    private static final Map<Class<?>, Mapper<?, ?>> MAPPER_MAP;

    static {
        MAPPER_MAP = new HashMap<>();
        // user entities
        MAPPER_MAP.put(User.class, new UserMapper());
        MAPPER_MAP.put(UserDTO.class, INSTANCE.get(User.class));
        MAPPER_MAP.put(Role.class, new RoleMapper());
        MAPPER_MAP.put(RoleDTO.class, INSTANCE.get(Role.class));
        MAPPER_MAP.put(Privilege.class, new PrivilegeMapper());
        MAPPER_MAP.put(PrivilegeDTO.class, INSTANCE.get(Privilege.class));
        // form entities
        MAPPER_MAP.put(Form.class, new FormMapper());
        MAPPER_MAP.put(FormDTO.class, INSTANCE.get(Form.class));
        MAPPER_MAP.put(FormConfig.class, new FormConfigMapper());
        MAPPER_MAP.put(FormConfigDTO.class, INSTANCE.get(FormConfig.class));
        MAPPER_MAP.put(Field.class, new FieldMapper());
        MAPPER_MAP.put(FieldDTO.class, INSTANCE.get(Field.class));
        MAPPER_MAP.put(FieldConfig.class, new FieldConfigMapper());
        MAPPER_MAP.put(FieldConfigDTO.class, INSTANCE.get(FieldConfig.class));
        MAPPER_MAP.put(FieldAttribute.class, new FieldAttributeMapper());
        MAPPER_MAP.put(FieldAttributeDTO.class, INSTANCE.get(FieldAttribute.class));
        MAPPER_MAP.put(FieldOption.class, new FieldOptionMapper());
        MAPPER_MAP.put(FieldOptionDTO.class, INSTANCE.get(FieldOption.class));
    }

    public <S, D> Mapper<S, D> get(Class<S> classType) {
        Mapper<?, ?> mapper = MAPPER_MAP.get(classType);

        if (mapper == null) {
            throw new MapperException("No registered mapper for source-type '%s'".formatted(classType.getName()));
        }

        return (Mapper<S, D>) mapper;
    }

}
