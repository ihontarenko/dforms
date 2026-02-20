package df.application.persistence.repository.form;

import df.application.persistence.entity.form.Form;
import df.application.persistence.entity.form.FormConfig;
import df.application.persistence.support.EntityGraphConstants;
import df.common.extensions.persistence.entity_graph.JpaEntityGraphRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormConfigRepository extends JpaEntityGraphRepository<FormConfig, String> {

    Optional<FormConfig> findByConfigName(String configName);

    @EntityGraph(EntityGraphConstants.FIELD_CONFIG_WITH_FORM)
    List<FormConfig> findAllByFormId(String formId);

    @EntityGraph(EntityGraphConstants.FIELD_CONFIG_WITH_FORM)
    List<FormConfig> findAllByForm(Form form);

}
