package df.base.jpa.form.repository;

import df.base.jpa.form.FieldAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldAttributeRepository extends JpaRepository<FieldAttribute, String> {

}
