package df.application.persistence.repository.form;

import df.application.persistence.entity.form.Field;
import df.application.persistence.entity.form.FieldAttribute;
import df.common.extensions.persistence.entity_graph.JpaEntityGraphRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldAttributeRepository extends JpaEntityGraphRepository<FieldAttribute, String> {

    List<FieldAttribute> findAllByField(Field form);

}
