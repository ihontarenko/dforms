package df.base.jpa.form;

import df.base.jpa.EntityGraphConstants;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormFieldConfigRepository extends JpaRepository<FormFieldConfig, String> {

    Optional<FormFieldConfig> findByConfigName(String configName);

    @EntityGraph(EntityGraphConstants.FORM_CONFIG_WITH_FORM)
    List<FormFieldConfig> findAllByFormFieldId(String formId);

    @EntityGraph(EntityGraphConstants.FORM_CONFIG_WITH_FORM)
    List<FormFieldConfig> findAllByFormField(FormField form);

}
