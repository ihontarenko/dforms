package df.base.mapping.form;

import df.base.persistence.entity.support.ElementType;
import df.base.persistence.entity.support.FieldStatus;
import df.base.persistence.entity.form.Field;
import df.base.common.Mapper;
import df.base.persistence.entity.support.UsageType;
import df.base.dto.form.FieldDTO;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public class FieldMapper implements Mapper<Field, FieldDTO> {

    private final FieldConfigMapper    configMapper    = new FieldConfigMapper();
    private final FieldAttributeMapper attributeMapper = new FieldAttributeMapper();
    private final FieldOptionMapper    optionMapper    = new FieldOptionMapper();

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
        destination.setId(source.getId());
        destination.setUsageType(source.getUsageType().name());
        destination.setElementType(source.getElementType().name());
        destination.setName(source.getName());
        destination.setLabel(source.getLabel());
        destination.setDescription(source.getDescription());
        destination.setStatus(source.getStatus().name());

        ofNullable(source.getConfigs()).ifPresent(configs
                -> configs.stream().map(configMapper::map).forEach(destination::addConfig));

        ofNullable(source.getAttributes()).ifPresent(attributes
                -> attributes.stream().map(attributeMapper::map).forEach(destination::addAttribute));

        ofNullable(source.getOptions()).ifPresent(options
                -> options.stream().map(optionMapper::map).forEach(destination::addOption));

        ofNullable(source.getChildren()).ifPresent(children
                -> children.stream().map(this::map).forEach(destination::addChild));

        ofNullable(source.getParents()).ifPresent(parents
                -> parents.stream().map(this::map).forEach(destination::addParent));
    }

    @Override
    public void reverse(FieldDTO source, Field destination) {
        destination.setUsageType(UsageType.valueOf(source.getUsageType()));
        destination.setElementType(ElementType.valueOf(source.getElementType()));
        destination.setName(source.getName());
        destination.setLabel(source.getLabel());
        destination.setDescription(source.getDescription());
        destination.setStatus(FieldStatus.valueOf(source.getStatus()));


        ofNullable(source.getConfigs()).ifPresent(configs
                -> configs.stream().map(configMapper::reverse).forEach(destination::addConfig));

        ofNullable(source.getAttributes()).ifPresent(attributes
                -> attributes.stream().map(attributeMapper::reverse).forEach(destination::addAttribute));

        ofNullable(source.getOptions()).ifPresent(options
                -> options.stream().map(optionMapper::reverse).forEach(destination::addOption));

        ofNullable(source.getChildren()).ifPresent(children
                -> children.stream().map(this::reverse).forEach(destination::addChild));

        ofNullable(source.getParents()).ifPresent(parents
                -> parents.stream().map(this::reverse).forEach(destination::addParent));
    }

}
