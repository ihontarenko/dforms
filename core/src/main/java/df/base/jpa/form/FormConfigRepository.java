package df.base.jpa.form;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormConfigRepository extends JpaRepository<FormConfig, String> {

    Optional<FormConfig> findByConfigName(String configName);

}
