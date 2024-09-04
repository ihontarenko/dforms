package df.base.jpa.form.repository;

import df.base.jpa.EntityGraphConstants;
import df.base.jpa.form.Field;
import df.base.jpa.form.FieldConfig;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FieldConfigRepository extends JpaRepository<FieldConfig, String> {

    Optional<FieldConfig> findByConfigName(String configName);

    @EntityGraph(EntityGraphConstants.FORM_CONFIG_WITH_FORM)
    List<FieldConfig> findAllByFieldId(String formId);

    @EntityGraph(EntityGraphConstants.FORM_CONFIG_WITH_FORM)
    List<FieldConfig> findAllByField(Field form);

}
