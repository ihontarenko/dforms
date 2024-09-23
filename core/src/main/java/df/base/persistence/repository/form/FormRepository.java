package df.base.persistence.repository.form;

import df.base.common.extensions.persistence.entity_graph.JpaEntityGraph;
import df.base.persistence.entity.form.Form;
import df.base.persistence.entity.user.User;
import df.base.persistence.support.EntityGraphConstants;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormRepository extends JpaRepository<Form, String> {

    @EntityGraph(EntityGraphConstants.FORM_WITH_USER)
    @Override
    List<Form> findAll();

    Optional<Form> findById(String id, JpaEntityGraph entityGraph);

    boolean existsByIdAndUser(String id, User user);

    @Query(value = "SELECT FLD.NAME " +
            "FROM DF_FORMS FRM " +
            "LEFT JOIN DF_FORM_FIELD_MAPPING M ON FRM.ID=M.FORM_ID " +
            "LEFT JOIN DF_FIELDS FLD ON FLD.ID=M.FIELD_ID " +
            "WHERE FRM.ID = :formId",
            nativeQuery = true
    )
    List<String> findFieldNamesByFormId(@Param("formId") String formId);

    @Query(value = "SELECT SEQUENCE_ORDER FROM DF_FORM_FIELD_MAPPING WHERE FORM_ID = :formId AND FIELD_ID = :fieldId",
            nativeQuery = true)
    Integer findCurrentOrder(@Param("formId") String formId, @Param("fieldId") String fieldId);

}
