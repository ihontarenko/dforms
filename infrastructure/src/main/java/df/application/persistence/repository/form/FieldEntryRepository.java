package df.application.persistence.repository.form;

import df.application.persistence.entity.form.FieldEntry;
import df.common.extensions.persistence.entity_graph.JpaEntityGraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldEntryRepository extends JpaEntityGraphRepository<FieldEntry, String> {

}
