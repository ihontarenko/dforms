package df.base.persistence.repository.form;

import df.base.persistence.entity.form.FieldEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldEntryRepository extends JpaRepository<FieldEntry, String> {

}
