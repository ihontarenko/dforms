package df.application.persistence.repository.form;

import df.application.persistence.entity.form.Field;
import df.application.persistence.entity.form.FieldOption;
import df.common.extensions.persistence.entity_graph.JpaEntityGraphRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldOptionRepository extends JpaEntityGraphRepository<FieldOption, String> {

    List<FieldOption> findAllByField(Field form);

}
