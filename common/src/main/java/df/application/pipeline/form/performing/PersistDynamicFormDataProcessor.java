package df.application.pipeline.form.performing;

import df.application.persistence.entity.form.Field;
import df.application.persistence.entity.form.FieldEntry;
import df.application.persistence.entity.form.Form;
import df.application.persistence.entity.form.FormEntry;
import df.application.persistence.repository.form.FieldEntryRepository;
import df.application.persistence.repository.form.FormEntryRepository;
import df.application.service.form.FieldEntryService;
import df.application.service.form.FieldService;
import df.application.service.form.FormService;
import org.jmouse.pipeline.PipelineResult;
import org.jmouse.pipeline.PipelineProcessor;
import org.jmouse.pipeline.context.PipelineContext;
import org.jmouse.core.context.mutable.MutableArgumentsContext;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class PersistDynamicFormDataProcessor implements PipelineProcessor {

    @Override
    public PipelineResult process(
            PipelineContext context, MutableArgumentsContext arguments, PipelineResult previous) throws Exception {
        FieldEntryService    entryService = arguments.getRequiredArgument(FieldEntryService.class);
        FieldEntryRepository repository   = entryService.getRepository();
        String               primaryId    = context.getValue("PRIMARY_ID");
        Map<String, Object>  requestData  = arguments.getArgument("DATA");
        Optional<Form>       form         = context.getBeanLookup().getBean(FormService.class).getById(primaryId);
        FieldService         fieldService = context.getBeanLookup().getBean(FieldService.class);
        FormEntryRepository entryRepository = context.getBeanLookup().getBean(FormEntryRepository.class);

        FormEntry formEntry = new FormEntry();
        form.ifPresent(formEntry::setForm);

        persist(requestData.entrySet(), fieldService, formEntry);

        entryRepository.save(formEntry);

        return PipelineResult.finish();
    }

    private void persist(Set<Map.Entry<String, Object>> entries, FieldService fieldService, FormEntry formEntry) {
        for (Map.Entry<String, Object> entry : entries) {
            if (entry.getValue() instanceof Map<?, ?> nested) {
                @SuppressWarnings("unchecked")
                Map<String, Object> nestedMap = (Map<String, Object>) nested;
                persist(nestedMap.entrySet(), fieldService, formEntry);
                continue;
            }

            FieldEntry e = new FieldEntry();
            fieldService.getByName(entry.getKey()).ifPresent(e::setField);
            e.setValue(entry.getValue().toString());
            e.setFormEntry(formEntry);
            formEntry.addFieldEntry(e);
        }
    }

}
