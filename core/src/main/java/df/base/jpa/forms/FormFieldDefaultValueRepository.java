package df.base.jpa.forms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormFieldDefaultValueRepository extends JpaRepository<FormFieldDefaultValue, String> {

}
