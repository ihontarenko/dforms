package df.base.service;

import df.base.common.exception.MethodNotImplemented;
import df.base.common.extensions.persistence.entity_graph.JpaEntityGraphRepository;
import df.base.dto.DTO;
import df.base.mapping.Mappers;
import df.base.persistence.exception.JpaResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static df.base.Messages.*;
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
        entity = (E) Mappers.INSTANCE.get(entity.getClass()).reverse(dto);

        getRepository().save(entity);

        return entity;
    }
    
    @Transactional
    default E createOrUpdate(D dto) {
        Optional<E> optional = getById(dto.getId());

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
    default void delete(E config) {
        getRepository().delete(config);
    }

    @Transactional
    default void deleteIfExists(String id) {
        getById(id).ifPresent(this::delete);
    }

    @Transactional
    default void deleteIfExists(E config) {
        Optional.ofNullable(config).ifPresent(this::delete);
    }
    
    R getRepository();
    
}
