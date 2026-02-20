package df.application.pipeline.form.performing;

import df.application.dto.form.FieldConfigDTO;
import df.application.old_mapping.form.FieldConfigMapper;
import org.jmouse.pipeline.PipelineResult;
import org.jmouse.pipeline.PipelineProcessor;
import org.jmouse.pipeline.context.PipelineContext;
import df.application.old_mapping.form.MultiValueMapMapper;
import df.application.persistence.entity.form.Field;
import df.application.persistence.entity.form.FieldConfig;
import df.application.service.form.FieldService;
import df.application.service.form.FormService;
import org.jmouse.core.context.beans.BeanLookup;
import org.jmouse.core.context.mutable.MutableArgumentsContext;
import org.jmouse.core.mapping.Mapper;
import org.jmouse.core.mapping.Mappers;
import org.jmouse.core.mapping.binding.TypeMappingRegistry;
import org.springframework.util.MultiValueMap;

import java.util.*;

public class PrepareRequestPostDataProcessor implements PipelineProcessor {

    @Override
    public PipelineResult process(
            PipelineContext context, MutableArgumentsContext arguments, PipelineResult previous) throws Exception {
        MultiValueMapMapper           mapper      = new MultiValueMapMapper();
        MultiValueMap<String, String> postData    = arguments.getRequiredArgument(MultiValueMap.class);
        Map<String, Object>           requestData = mapper.map(postData);

        // need to fulfil absent names with NULLs for correct validation
        fulfilAbsentFieldWithNulls(context, requestData);

        Map<String, Field> fields = getFields(context, requestData.keySet());

        arguments.setArgument("REQUEST_DATA", requestData);
        arguments.setArgument("FIELDS", fields);
        arguments.setArgument("VALIDATION_CONFIGS", getValidationConfigs(context, fields));

        return PipelineResult.of(ReturnCodes.INITIALIZE_VALIDATORS);
    }

    private void fulfilAbsentFieldWithNulls(PipelineContext context, Map<String, Object> requestData) {
        BeanLookup   beanLookup = context.getBeanLookup();
        String       primaryId  = context.getArgumentsContext().getRequiredArgument("PRIMARY_ID");
        FormService  service    = beanLookup.getBean(FormService.class);
        List<String> fieldNames = service.getFieldNames(primaryId);
        fieldNames.forEach(name -> requestData.putIfAbsent(name, null));
    }

    private Map<String, Field> getFields(PipelineContext context, Set<String> fieldNames) {
        BeanLookup         lookup = context.getBeanLookup();
        Map<String, Field> fields = new HashMap<>();

        for (Field field : lookup.getBean(FieldService.class).getAllByNames(fieldNames)) {
            fields.put(field.getName(), field);
        }

        return fields;
    }

    private Map<String, List<FieldConfigDTO>> getValidationConfigs(PipelineContext context, Map<String, Field> fields) {
        BeanLookup                        lookup            = context.getBeanLookup();
        Map<String, List<FieldConfigDTO>> validationConfigs = new HashMap<>();
        FieldConfigMapper                 mapper            = lookup.getBean(FieldConfigMapper.class);

        for (Map.Entry<String, Field> entry : fields.entrySet()) {
            validationConfigs.putIfAbsent(entry.getKey(), new ArrayList<>());
            for (FieldConfig config : entry.getValue().getConfigs()) {
                FieldConfigDTO configDTO = mapper.map(config);
                mapper().map(config, FieldConfigDTO.class);
                if (configDTO.getKey().startsWith("#validation")) {
                    validationConfigs.get(entry.getKey()).add(configDTO);
                }
            }
        }

        return validationConfigs;
    }

    public static Mapper mapper() {
        return Mappers.builder()
                .registry(TypeMappingRegistry.builder()
                                  .mapping(FieldConfig.class, FieldConfigDTO.class, m -> m
                                          .property("fieldId", c -> c.reference("field.id"))
                                          .property("key", c -> c.reference("configName"))
                                          .property("value", c -> c.reference("configValue"))
                                          .build()
                                  )
                                  .build())
                .build();
    }

}
