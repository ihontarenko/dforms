package df.application.persistence.repository.form;

import df.application.persistence.entity.form.FieldDefaultValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldDefaultValueRepository extends JpaRepository<FieldDefaultValue, String> {

}
