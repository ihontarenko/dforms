package df.base.jpa.form;

import df.base.jpa.EntityGraphConstants;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepository extends JpaRepository<Form, String> {

    @EntityGraph(EntityGraphConstants.FORM_WITH_USER)
    @Override
    List<Form> findAll();

}
