package df.application.persistence.repository.form;

import df.application.persistence.entity.form.Field;
import df.application.persistence.entity.form.FieldConfig;
import df.application.persistence.support.EntityGraphConstants;
import df.common.extensions.persistence.entity_graph.JpaEntityGraphRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FieldConfigRepository extends JpaEntityGraphRepository<FieldConfig, String> {

    Optional<FieldConfig> findByConfigName(String configName);

    @EntityGraph(EntityGraphConstants.FIELD_CONFIG_WITH_FORM)
    List<FieldConfig> findAllByFieldId(String formId);

    @EntityGraph(EntityGraphConstants.FIELD_CONFIG_WITH_FORM)
    List<FieldConfig> findAllByField(Field form);

}
