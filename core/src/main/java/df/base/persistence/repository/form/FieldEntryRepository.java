package df.base.jpa.form.repository;

import df.base.jpa.form.FieldEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldEntryRepository extends JpaRepository<FieldEntry, String> {

}
