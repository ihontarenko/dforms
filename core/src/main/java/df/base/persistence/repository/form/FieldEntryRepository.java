package df.base.persistence.repository.form;

import df.base.common.extensions.persistence.entity_graph.JpaEntityGraphRepository;
import df.base.persistence.entity.form.FieldEntry;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldEntryRepository extends JpaEntityGraphRepository<FieldEntry, String> {

}
