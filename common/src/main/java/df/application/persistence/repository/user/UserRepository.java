package df.application.persistence.repository.user;

import df.application.persistence.entity.user.User;
import df.application.persistence.support.EntityGraphConstants;
import df.application.security.Provider;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @EntityGraph(EntityGraphConstants.USER_WITH_ROLES)
    List<User> findAll();

    Optional<User> findByEmail(String email);

    List<User> findAllByEmail(String email);

    Optional<User> findByEmailAndProvider(String email, Provider provider);

}
