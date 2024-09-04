package df.base.persistence.repository.user;

import df.base.persistence.entity.user.Role;
import df.base.persistence.support.EntityGraphConstants;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findByName(String name);

    List<Role> findByNameIn(Iterable<String> names);

    @EntityGraph(EntityGraphConstants.ROLE_WITH_PRIVILEGE)
    List<Role> findAll();

}
