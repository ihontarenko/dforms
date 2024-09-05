package df.base.persistence.repository.form;

import df.base.common.extensions.persistence.entity_graph.JpaEntityGraphRepository;
import df.base.persistence.entity.form.Field;
import df.base.persistence.entity.form.FieldAttribute;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldAttributeRepository extends JpaEntityGraphRepository<FieldAttribute, String> {

    List<FieldAttribute> findAllByField(Field form);

}
