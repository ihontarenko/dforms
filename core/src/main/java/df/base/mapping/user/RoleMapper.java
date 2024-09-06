package df.base.mapping.user;

import df.base.persistence.entity.user.Privilege;
import df.base.persistence.entity.user.Role;
import df.base.common.Mapper;
import df.base.dto.user.RoleDTO;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

@Service
public class RoleMapper implements Mapper<Role, RoleDTO> {

    @Override
    public RoleDTO map(Role source) {
        RoleDTO roleDTO = new RoleDTO();

        map(source, roleDTO);

        return roleDTO;
    }

    @Override
    public Role reverse(RoleDTO source) {
        Role role = new Role();

        reverse(source, role);

        return role;
    }

    @Override
    public void reverse(RoleDTO source, Role destination) {
        destination.setName(source.getName());
        destination.setId(source.id());
    }

    @Override
    public void map(Role source, RoleDTO destination) {
        destination.setName(source.getName());
        destination.setId(source.getId());
        destination.setPrivileges(source.getPrivileges()
                .stream().map(Privilege::getName).collect(toList()));
    }
}
