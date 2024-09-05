package df.base.persistence.repository.form;

import df.base.common.extensions.persistence.entity_graph.JpaEntityGraphRepository;
import df.base.persistence.entity.form.Field;
import df.base.persistence.entity.form.FieldOption;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldOptionRepository extends JpaEntityGraphRepository<FieldOption, String> {

    List<FieldOption> findAllByField(Field form);

}
