package df.base.persistence.repository.form;

import df.base.persistence.support.EntityGraphConstants;
import df.base.persistence.entity.form.Form;
import df.base.persistence.entity.form.FormConfig;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormConfigRepository extends JpaRepository<FormConfig, String> {

    Optional<FormConfig> findByConfigName(String configName);

    @EntityGraph(EntityGraphConstants.FORM_CONFIG_WITH_FORM)
    List<FormConfig> findAllByFormId(String formId);

    @EntityGraph(EntityGraphConstants.FORM_CONFIG_WITH_FORM)
    List<FormConfig> findAllByForm(Form form);

}
