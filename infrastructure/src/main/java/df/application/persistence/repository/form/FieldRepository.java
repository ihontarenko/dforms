package df.application.persistence.repository.form;

import df.application.persistence.entity.form.Field;
import df.application.persistence.entity.support.UsageType;
import df.application.persistence.support.EntityGraphConstants;
import df.common.extensions.persistence.entity_graph.JpaEntityGraphRepository;
import df.common.extensions.persistence.entity_graph.JpaEntityGraph;
import df.application.persistence.entity.support.FieldStatus;
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

    List<Field> findAllByUsageType(UsageType usageType, JpaEntityGraph graph);

    List<Field> findAllByUsageTypeIn(Iterable<UsageType> usageTypes, JpaEntityGraph graph);

    List<Field> findAllByNameIn(Iterable<String> names, JpaEntityGraph graph);

    List<Field> findAllByUsageTypeInAndStatus(Iterable<UsageType> usageTypes, FieldStatus status, JpaEntityGraph graph);

    @EntityGraph(EntityGraphConstants.FORM_FIELD_FULL)
    Optional<Field> findById(@Param("id") String id);

    Optional<Field> findById(@Param("id") String id, JpaEntityGraph graph);

    List<Field> findByStatus(FieldStatus status, JpaEntityGraph graph);

}
