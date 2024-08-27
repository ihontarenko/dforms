package df.base.service.form;

import df.base.service.RedirectAware;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ServiceInterface<ID, DTO, T, R> extends RedirectAware {
    
    @Transactional
    default T create(R related, DTO dto) {
        throw new UnsupportedOperationException();
    }

    @Transactional
    default T create(DTO dto) {
        throw new UnsupportedOperationException();
    }

    @Transactional
    default T update(T entity, DTO dto) {
        throw new UnsupportedOperationException();
    }

    @Transactional
    default T createOrUpdate(R related, DTO dto) {
        throw new UnsupportedOperationException();
    }

    @Transactional
    default T createOrUpdate(DTO dto) {
        throw new UnsupportedOperationException();
    }

    @Transactional(readOnly = true)
    default List<T> getAll() {
        throw new UnsupportedOperationException();
    }

    @Transactional(readOnly = true)
    default Optional<T> getById(ID id) {
        throw new UnsupportedOperationException();
    }

    @Transactional(readOnly = true)
    default T requireById(ID id) {
        throw new UnsupportedOperationException();
    }
    
}
