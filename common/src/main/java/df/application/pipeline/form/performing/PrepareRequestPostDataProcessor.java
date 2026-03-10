package df.application.pipeline.form.performing;

import df.application.dto.form.FieldConfigDTO;
import org.jmouse.core.parameters.RequestParametersJavaStructureConverter;
import org.jmouse.core.parameters.RequestParametersJavaStructureOptions;
import org.jmouse.core.parameters.RequestParametersTree;
import org.jmouse.core.parameters.RequestParametersTreeParser;
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

    private Object parseParameters(MultiValueMap<String, String> values) {
        Map<String, String[]> parameters = new HashMap<>();

        values.forEach((k, v) -> parameters.put(k, v.toArray(new String[0])));

        RequestParametersTreeParser treeParser = new RequestParametersTreeParser();
        RequestParametersTree       tree       = treeParser.parse(parameters);

        RequestParametersJavaStructureConverter converter = new RequestParametersJavaStructureConverter(
                RequestParametersJavaStructureOptions.defaults());

        return converter.toJavaObject(tree);
    }

    @Override
    public PipelineResult process(
            PipelineContext context, MutableArgumentsContext arguments, PipelineResult previous
    ) throws Exception {
        MultiValueMapMapper           mapper      = new MultiValueMapMapper();
        MultiValueMap<String, String> postData    = arguments.getRequiredArgument(MultiValueMap.class);
        Map<String, Object>           requestData = mapper.map(postData);

        // need to fulfil absent names with NULLs for correct validation
        fulfilAbsentFieldWithNulls(context, requestData);

        Map<String, Field> fields = getFields(context, requestData.keySet());

        arguments.setArgument("REQUEST_DATA", requestData);
        arguments.setArgument("DATA", parseParameters(postData));
        arguments.setArgument("FIELDS", fields);
        arguments.setArgument("VALIDATION_CONFIGS", getValidationConfigs(fields));

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

    private Map<String, List<FieldConfigDTO>> getValidationConfigs(Map<String, Field> fields) {
        Mapper                            mapper            = mapper();
        Map<String, List<FieldConfigDTO>> validationConfigs = new LinkedHashMap<>();

        for (Map.Entry<String, Field> entry : fields.entrySet()) {
            String path  = entry.getKey();
            Field  field = entry.getValue();
            collectValidationConfigurations(path, field, validationConfigs, mapper);
        }

        return validationConfigs;
    }

    private void collectValidationConfigurations(
            String path,
            Field field,
            Map<String, List<FieldConfigDTO>> configurations,
            Mapper mapper
    ) {
        configurations.putIfAbsent(path, new ArrayList<>());

        boolean hasChildren = !field.getChildren().isEmpty();

        for (FieldConfig config : field.getConfigs()) {
            FieldConfigDTO configDTO = mapper.map(config, FieldConfigDTO.class);
            String         name      = configDTO.getKey();
            if (name != null && name.startsWith("#validation") && !hasChildren) {
                configurations.get(path).add(configDTO);
            }
        }

        for (Field child : field.getChildren()) {
            String childPath = getChildPath(path, child);
            collectValidationConfigurations(childPath, child, configurations, mapper);
        }
    }

    private String getChildPath(String parentPath, Field child) {
        String childName = child.getName();

        if (parentPath == null || parentPath.isBlank()) {
            return childName;
        }

        return parentPath + "." + childName;
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
