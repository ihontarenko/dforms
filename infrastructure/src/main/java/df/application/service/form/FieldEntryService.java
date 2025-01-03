package df.application.service.form;

import df.application.dto.form.FieldEntryDTO;
import df.application.persistence.entity.form.FieldEntry;
import df.application.service.CommonService;
import df.application.persistence.repository.form.FieldEntryRepository;

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
