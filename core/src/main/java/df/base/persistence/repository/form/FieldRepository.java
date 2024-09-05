package df.base.persistence.repository.form;

import df.base.common.extensions.persistence.entity_graph.JpaEntityGraphRepository;
import df.base.common.extensions.persistence.entity_graph.JpaEntityGraph;
import df.base.persistence.support.EntityGraphConstants;
import df.base.persistence.entity.form.Field;
import df.base.persistence.entity.support.FieldStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FieldRepository extends JpaEntityGraphRepository<Field, String> {

    @Override
    @EntityGraph(EntityGraphConstants.FORM_FIELD_WITH_CONFIGS)
    List<Field> findAll();

    @EntityGraph(EntityGraphConstants.FORM_FIELD_WITH_ALL_RELATED)
    Optional<Field> findById(@Param("id") String id);

    Optional<Field> findById(@Param("id") String id, JpaEntityGraph graph);

    List<Field> findByStatus(FieldStatus status, JpaEntityGraph graph);

}
