package df.base.jpa.forms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormFieldConfigRepository extends JpaRepository<FormFieldConfig, String> {

}
