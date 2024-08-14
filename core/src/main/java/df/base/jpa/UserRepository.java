package df.base.jpa;

import df.base.security.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    List<User> findAllByEmail(String email);

    Optional<User> findByEmailAndProvider(String email, Provider provider);

}
