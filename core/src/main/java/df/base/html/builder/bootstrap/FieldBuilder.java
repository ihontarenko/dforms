package df.base.html.builder.bootstrap;

import df.base.common.elements.Node;
import df.base.common.elements.TagName;
import df.base.common.elements.builder.Builder;
import df.base.common.elements.builder.BuilderContext;
import df.base.common.elements.node.ElementNode;
import df.base.common.elements.node.TextNode;
import df.base.common.libs.jbm.StringUtils;
import df.base.dto.form.FieldAttributeDTO;
import df.base.dto.form.FieldDTO;
import df.base.dto.form.FieldOptionDTO;
import df.base.persistence.entity.support.ElementType;

import java.util.Collection;
import java.util.List;

import static df.base.persistence.entity.support.ElementType.valueOf;

public class FieldBuilder implements Builder<FieldDTO> {

    @Override
    public Node build(FieldDTO fieldDTO, BuilderContext ctx) {
        return switch (valueOf(fieldDTO.getElementType())) {
            case TEXT, NUMBER, URL, EMAIL, DATE -> createTextInput(fieldDTO);
            case SELECT -> createSelectOptions(fieldDTO);
            case TEXTAREA -> createTextarea(fieldDTO);
            case CHECKBOX, RADIO -> createInputOptions(fieldDTO);
            case NONE -> createVirtual(fieldDTO, ctx);
        };
    }

    private void addElementAttributes(Node node, List<FieldAttributeDTO> attributes) {
        for (FieldAttributeDTO attribute : attributes) {
            node.addAttribute(attribute.getKey(), attribute.getValue());
        }
    }

    private Node createTextInput(FieldDTO fieldDTO) {
        Node root  = new ElementNode(TagName.DIV);
        Node input = new ElementNode(TagName.INPUT);
        Node label = new ElementNode(TagName.LABEL);
        Node small = new ElementNode(TagName.SMALL);

        label.addChild(new TextNode(fieldDTO.getLabel()));
        label.addAttribute("for", fieldDTO.id());
        label.addAttribute("class", "form-label");

        input.addAttribute("type", fieldDTO.getElementType());
        input.addAttribute("placeholder", fieldDTO.getLabel());
        input.addAttribute("id", fieldDTO.id());
        input.addAttribute("class", "form-control");

        addElementAttributes(input, fieldDTO.getAttributes());

        root.addChild(label);
        root.addChild(input);

        if (StringUtils.hasText(fieldDTO.getDescription())) {
            small.addChild(new TextNode(fieldDTO.getDescription()));
            root.addChild(small);
        }

        return root;
    }

    private Node createInputOptions(FieldDTO fieldDTO) {
        Node root = new ElementNode(TagName.DIV);

        for (FieldOptionDTO option : fieldDTO.getOptions()) {
            Node wrapper = new ElementNode(TagName.DIV);
            Node input   = new ElementNode(TagName.INPUT);

            input.addAttribute("type", fieldDTO.getElementType());
            input.addAttribute("name", fieldDTO.getName());
            input.addAttribute("value", option.getKey());

            addElementAttributes(input, fieldDTO.getAttributes());

            Node label = new ElementNode(TagName.LABEL);

            label.addChild(new TextNode(option.getValue()));

            wrapper.addChild(input);
            wrapper.addChild(label);

            root.addChild(wrapper);
        }

        return root;
    }

    private Node createSelectOptions(FieldDTO fieldDTO) {
        Node select = new ElementNode(TagName.SELECT);

        select.addAttribute("name", fieldDTO.getName());
        addElementAttributes(select, fieldDTO.getAttributes());

        for (FieldOptionDTO option : fieldDTO.getOptions()) {
            select.addChild(createSelectOption(option));
        }

        return select;
    }

    public Node createSelectOption(FieldOptionDTO option) {
        Node root = new ElementNode(TagName.OPTION);

        root.addAttribute("value", option.getKey());
        root.addChild(new TextNode(option.getValue()));

        return root;
    }

    private Node createTextarea(FieldDTO fieldDTO) {
        Node textarea = new ElementNode(TagName.TEXTAREA);

        addElementAttributes(textarea, fieldDTO.getAttributes());

        return textarea;
    }

    private Node createVirtual(FieldDTO fieldDTO, BuilderContext ctx) {
        Node                 node     = new ElementNode(TagName.DIV);
        Collection<FieldDTO> children = fieldDTO.getChildren().values();

        addElementAttributes(node, fieldDTO.getAttributes());

        for (FieldDTO child : children) {
            node.addChild(build(child, ctx));
        }

        return node;
    }

}
