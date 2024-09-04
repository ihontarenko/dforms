package df.base.jpa.form.repository;

import df.base.jpa.form.FieldOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldOptionRepository extends JpaRepository<FieldOption, String> {

}
