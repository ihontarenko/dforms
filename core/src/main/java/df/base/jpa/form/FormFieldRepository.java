package df.base.jpa.form;

import df.base.jpa.EntityGraphConstants;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormFieldRepository extends JpaRepository<FormField, String> {

    @EntityGraph(EntityGraphConstants.FORM_FIELD_WITH_CONFIGS)
    List<FormFieldConfig> findAllWithConfigs();

    @EntityGraph(EntityGraphConstants.FORM_FIELD_WITH_ALL_RELATED)
    List<FormFieldConfig> findAllWithAllRelated();

    @EntityGraph(EntityGraphConstants.FORM_FIELD_WITH_ALL_RELATED)
    Optional<FormField> findByIdWithAllRelated(String id);

}
