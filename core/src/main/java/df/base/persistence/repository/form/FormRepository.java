package df.base.persistence.repository.form;

import df.base.persistence.entity.form.Form;
import df.base.persistence.entity.user.User;
import df.base.persistence.support.EntityGraphConstants;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepository extends JpaRepository<Form, String> {

    @EntityGraph(EntityGraphConstants.FORM_WITH_USER)
    @Override
    List<Form> findAll();

    boolean existsByIdAndUser(String id, User user);

    @Query(value = "SELECT SEQUENCE_ORDER FROM DF_FORM_FIELD_MAPPING WHERE FORM_ID = :formId AND FIELD_ID = :fieldId",
            nativeQuery = true)
    Integer findCurrentOrder(@Param("formId") String formId, @Param("fieldId") String fieldId);

}
