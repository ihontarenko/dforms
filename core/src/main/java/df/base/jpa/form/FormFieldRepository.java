package df.base.jpa.form;

import df.base.internal.spring.jpa.entity_graph.ExtendedJpaRepository;
import df.base.internal.spring.jpa.entity_graph.JpaEntityGraph;
import df.base.jpa.EntityGraphConstants;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormFieldRepository extends ExtendedJpaRepository<FormField, String> {

//    @Override
//    @EntityGraph(EntityGraphConstants.FORM_FIELD_WITH_CONFIGS)
//    Set<FormField> findAll();

    @Override
    @EntityGraph(EntityGraphConstants.FORM_FIELD_WITH_CONFIGS)
    List<FormField> findAll();

    @EntityGraph(EntityGraphConstants.FORM_FIELD_WITH_ALL_RELATED)
    Optional<FormField> findById(@Param("id") String id);

    List<FormField> findByStatus(FieldStatus status, JpaEntityGraph graph);

}
