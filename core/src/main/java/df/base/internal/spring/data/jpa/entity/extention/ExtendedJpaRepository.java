package df.base.internal.spring.data.jpa.entity.extention;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExtendedJpaRepository<T, ID> extends JpaRepository<T, ID> {

    List<T> findAll(EntityGraph graph);

}
