package df.base.mapper.user;

import df.base.jpa.Privilege;
import df.base.common.Mapper;
import df.base.model.user.PrivilegeDTO;

import static df.base.utils.SlugifyTransliterator.slugify;

public class PrivilegeMapper implements Mapper<Privilege, PrivilegeDTO> {

    @Override
    public PrivilegeDTO map(Privilege source) {
        PrivilegeDTO privilegeDTO = new PrivilegeDTO();

        map(source, privilegeDTO);

        return privilegeDTO;
    }

    @Override
    public Privilege reverse(PrivilegeDTO source) {
        Privilege privilege = new Privilege();

        reverse(source, privilege);

        return privilege;
    }

    @Override
    public void reverse(PrivilegeDTO source, Privilege destination) {
        destination.setName(slugify(source.getName()).toUpperCase());
        destination.setId(source.getId());
    }

    @Override
    public void map(Privilege source, PrivilegeDTO destination) {
        destination.setName(source.getName());
        destination.setId(source.getId());
    }
}
