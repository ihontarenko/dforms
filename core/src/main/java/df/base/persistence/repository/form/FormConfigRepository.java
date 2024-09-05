package df.base.persistence.repository.form;

import df.base.common.extensions.persistence.entity_graph.JpaEntityGraphRepository;
import df.base.persistence.support.EntityGraphConstants;
import df.base.persistence.entity.form.Form;
import df.base.persistence.entity.form.FormConfig;
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
