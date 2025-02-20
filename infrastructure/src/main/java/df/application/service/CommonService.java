package df.application.service;

import df.application.dto.DTO;
import df.application.exception.MethodNotImplemented;
import df.common.extensions.persistence.entity_graph.JpaEntityGraphRepository;
import df.application.persistence.exception.JpaResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static df.application.Messages.*;
import static java.util.Objects.requireNonNull;

public interface CommonService<D extends DTO, E, R extends JpaEntityGraphRepository<E, String>> {
    
    MethodNotImplemented METHOD_NOT_IMPLEMENTED = new MethodNotImplemented(
            "This service method in not implemented yet");
    
    @Transactional
    default E create(D dto) {
        throw METHOD_NOT_IMPLEMENTED;
    }

    @Transactional
    default E update(E entity, D dto) {
        throw METHOD_NOT_IMPLEMENTED;
    }
    
    @Transactional
    default E createOrUpdate(D dto) {
        Optional<E> optional = getById(dto.id());

        if (optional.isPresent()) {
            return update(optional.get(), dto);
        } else {
            return create(dto);
        }
    }

    @Transactional(readOnly = true)
    default List<E> getAll() {
        return getRepository().findAll();
    }

    @Transactional(readOnly = true)
    default Optional<E> getById(String id) {
        return StringUtils.hasText(id) ? getRepository().findById(id) : Optional.empty();
    }

    @Transactional(readOnly = true)
    default E requireById(String id) {
        return getById(requireNonNull(id, REQUIRED_ID_CANNOT_BE_NULL))
                .orElseThrow(() -> new JpaResourceNotFoundException(ENTITY_NOT_FOUND.formatted(id)));
    }

    @Transactional
    default void delete(E entity) {
        getRepository().delete(entity);
    }

    @Transactional
    default void deleteIfExists(String id) {
        getById(id).ifPresent(this::delete);
    }

    @Transactional
    default void deleteIfExists(E entity) {
        Optional.ofNullable(entity).ifPresent(this::delete);
    }
    
    R getRepository();
    
}
