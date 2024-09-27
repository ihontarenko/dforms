package df.base.service.form;

import df.base.dto.form.FieldEntryDTO;
import df.base.persistence.entity.form.FieldEntry;
import df.base.persistence.repository.form.FieldEntryRepository;
import df.base.service.CommonService;

public class FieldEntryService implements CommonService<FieldEntryDTO, FieldEntry, FieldEntryRepository> {

    private final FieldEntryRepository repository;

    public FieldEntryService(FieldEntryRepository repository) {
        this.repository = repository;
    }

    @Override
    public FieldEntryRepository getRepository() {
        return repository;
    }

}
