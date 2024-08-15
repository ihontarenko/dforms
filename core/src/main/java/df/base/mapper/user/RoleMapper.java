package df.base.mapper.user;

import df.base.jpa.Privilege;
import df.base.jpa.Role;
import df.base.mapper.Mapper;
import df.base.model.user.RoleDTO;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
        destination.setId(source.getId());
    }

    @Override
    public void map(Role source, RoleDTO destination) {
        destination.setName(source.getName());
        destination.setId(source.getId());
        destination.setPrivileges(source.getPrivileges()
                .stream().map(Privilege::getName).collect(toList()));
    }
}
