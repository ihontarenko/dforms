package df.base.jpa.form.repository;

import df.base.jpa.form.FieldDefaultValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldDefaultValueRepository extends JpaRepository<FieldDefaultValue, String> {

}
