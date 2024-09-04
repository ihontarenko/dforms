package df.base.persistence.repository.form;

import df.base.persistence.support.EntityGraphConstants;
import df.base.persistence.entity.user.User;
import df.base.persistence.entity.form.Form;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepository extends JpaRepository<Form, String> {

    @EntityGraph(EntityGraphConstants.FORM_WITH_USER)
    @Override
    List<Form> findAll();

    boolean existsByIdAndUser(String id, User user);

}
