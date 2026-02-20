package df.application.persistence.repository.user;

import df.application.persistence.entity.user.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, String> {

    Optional<Privilege> findByName(String name);

    List<Privilege> findByNameIn(Iterable<String> names);

}
