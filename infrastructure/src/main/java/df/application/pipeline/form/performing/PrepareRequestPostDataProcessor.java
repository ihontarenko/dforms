package df.application.pipeline.form.performing;

import df.application.dto.form.FieldConfigDTO;
import df.application.mapping.form.FieldConfigMapper;
import df.common.context.ArgumentsContext;
import df.common.pipeline.PipelineProcessor;
import df.common.pipeline.context.PipelineContext;
import df.application.mapping.form.MultiValueMapMapper;
import df.application.persistence.entity.form.Field;
import df.application.persistence.entity.form.FieldConfig;
import df.application.service.form.FieldService;
import df.application.service.form.FormService;
import org.springframework.util.MultiValueMap;

import java.util.*;

public class PrepareRequestPostDataProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        MultiValueMapMapper           mapper      = new MultiValueMapMapper();
        MultiValueMap<String, String> postData    = arguments.requireArgument(MultiValueMap.class);
        Map<String, Object>           requestData = mapper.map(postData);

        // need to fulfil absent names with NULLs for correct validation
        fulfilAbsentFieldWithNulls(context, requestData);

        Map<String, Field> fields = getFields(context, requestData.keySet());

        arguments.setArgument("REQUEST_DATA", requestData);
        arguments.setArgument("FIELDS", fields);
        arguments.setArgument("VALIDATION_CONFIGS", getValidationConfigs(context, fields));

        return ReturnCodes.INITIALIZE_VALIDATORS;
    }

    private void fulfilAbsentFieldWithNulls(PipelineContext context, Map<String, Object> requestData) {
        String       primaryId  = context.getArgumentsContext().requireArgument("PRIMARY_ID");
        FormService  service    = context.getBean(FormService.class);
        List<String> fieldNames = service.getFieldNames(primaryId);
        fieldNames.forEach(name -> requestData.putIfAbsent(name, null));
    }

    private Map<String, Field> getFields(PipelineContext context, Set<String> fieldNames) {
        Map<String, Field> fields = new HashMap<>();

        for (Field field : context.getBean(FieldService.class).getAllByNames(fieldNames)) {
            fields.put(field.getName(), field);
        }

        return fields;
    }

    private Map<String, List<FieldConfigDTO>> getValidationConfigs(PipelineContext context, Map<String, Field> fields) {
        Map<String, List<FieldConfigDTO>> validationConfigs = new HashMap<>();
        FieldConfigMapper                 mapper            = context.getBean(FieldConfigMapper.class);

        for (Map.Entry<String, Field> entry : fields.entrySet()) {
            validationConfigs.putIfAbsent(entry.getKey(), new ArrayList<>());
            for (FieldConfig config : entry.getValue().getConfigs()) {
                FieldConfigDTO configDTO = mapper.map(config);
                if (configDTO.getKey().startsWith("#validation")) {
                    validationConfigs.get(entry.getKey()).add(configDTO);
                }
            }
        }

        return validationConfigs;
    }

}
