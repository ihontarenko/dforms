package df.base.jpa.form;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormFieldDefaultValueRepository extends JpaRepository<FormFieldDefaultValue, String> {

}