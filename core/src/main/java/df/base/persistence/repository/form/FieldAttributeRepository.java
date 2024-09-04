package df.base.persistence.repository.form;

import df.base.persistence.entity.form.FieldAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldAttributeRepository extends JpaRepository<FieldAttribute, String> {

}
