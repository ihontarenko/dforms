package df.base.persistence.repository.form;

import df.base.persistence.entity.form.FieldOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldOptionRepository extends JpaRepository<FieldOption, String> {

}
