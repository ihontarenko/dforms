package df.application.mapping.form;

import df.application.dto.form.FieldDTO;
import org.jmouse.common.mapping.Mapper;
import df.application.persistence.entity.form.Field;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.function.Consumer;

import static df.application.persistence.entity.support.UsageType.VIRTUAL;
import static java.util.Optional.ofNullable;

@Service
public class DeepFieldMapper implements Mapper<Field, FieldDTO> {

    private static final Consumer<Field>      NULL_RELATED    = field -> {
        // prevent lazy loading for child entities
        field.setChildren(null);
        field.setParents(null);
    };
    private final        FieldConfigMapper    configMapper    = new FieldConfigMapper();
    private final        FieldAttributeMapper attributeMapper = new FieldAttributeMapper();
    private final        FieldOptionMapper    optionMapper    = new FieldOptionMapper();

    @Override
    public FieldDTO map(Field source) {
        FieldDTO fieldDTO = new FieldDTO();

        map(source, fieldDTO);

        return fieldDTO;
    }

    @Override
    public Field reverse(FieldDTO source) {
        Field entity = new Field();

        reverse(source, entity);

        return entity;
    }

    @Override
    public void map(Field source, FieldDTO destination) {
        new FieldMapper().map(source, destination);

        ofNullable(source.getConfigs()).ifPresent(configs
            -> configs.stream().map(configMapper::map).forEach(destination::addConfig));

        ofNullable(source.getAttributes()).ifPresent(attributes
            -> attributes.stream().map(attributeMapper::map).forEach(destination::addAttribute));

        ofNullable(source.getOptions()).ifPresent(options
            -> options.stream().map(optionMapper::map).forEach(destination::addOption));

        if (source.getUsageType() == VIRTUAL) {
            ofNullable(source.getChildren()).ifPresent(children
                -> children.stream().peek(NULL_RELATED).map(this::map).forEach(destination::addChild));
        }
    }

    @Override
    public void reverse(FieldDTO source, Field destination) {
        new FieldMapper().reverse(source, destination);

        ofNullable(source.getConfigs()).ifPresent(configs
                -> configs.stream().map(configMapper::reverse).forEach(destination::addConfig));

        ofNullable(source.getAttributes()).ifPresent(attributes
                -> attributes.stream().map(attributeMapper::reverse).forEach(destination::addAttribute));

        ofNullable(source.getOptions()).ifPresent(options
                -> options.stream().map(optionMapper::reverse).forEach(destination::addOption));

        ofNullable(source.getChildren()).ifPresent(children
                -> children.values().stream().map(this::reverse).forEach(destination::addChild));

        ofNullable(source.getParents()).ifPresent(parents -> parents.values()
                .stream().peek(dto -> dto.setChildren(new HashMap<>()))
                .map(new FieldMapper()::reverse).forEach(destination::addParent));
    }

}
