package df.base.mapping.user;

import df.base.persistence.entity.user.Privilege;
import df.base.common.Mapper;
import df.base.dto.user.PrivilegeDTO;
import org.springframework.stereotype.Service;

import static df.base.common.support.SlugifyTransliterator.slugify;

@Service
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
        destination.setId(source.id());
    }

    @Override
    public void map(Privilege source, PrivilegeDTO destination) {
        destination.setName(source.getName());
        destination.setId(source.getId());
    }
}
