package df.application.persistence.repository.form;

import df.application.persistence.entity.form.Form;
import df.application.persistence.entity.form.FormEntry;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormEntryRepository extends JpaRepository<FormEntry, String> {

    @EntityGraph(value = "FormEntries.withFieldEntries", type = EntityGraph.EntityGraphType.FETCH)
    List<FormEntry> findAllByForm(Form form);

}
