package df.base.html.builder.bootstrap;

import df.base.common.elements.Node;
import df.base.common.elements.TagName;
import df.base.common.elements.builder.Builder;
import df.base.common.elements.builder.BuilderContext;
import df.base.common.elements.node.ElementNode;
import df.base.common.elements.node.TextNode;
import df.base.dto.form.FieldAttributeDTO;
import df.base.dto.form.FieldDTO;
import df.base.dto.form.FieldOptionDTO;
import df.base.persistence.entity.support.ElementType;

import java.util.Collection;

public class FieldBuilder implements Builder<FieldDTO> {

    @Override
    public Node build(FieldDTO fieldDTO, BuilderContext ctx) {
        ElementType elementType = ElementType.valueOf(fieldDTO.getElementType());

        // create field by type
        Node root = switch (elementType) {
            case TEXT, NUMBER, URL, EMAIL, DATE -> createTextInput(fieldDTO);
            case SELECT -> createSelectOptions(fieldDTO);
            case TEXTAREA -> createTextarea(fieldDTO);
            case CHECKBOX, RADIO -> createInputOptions(fieldDTO);
            case NONE -> createVirtual(fieldDTO, ctx);
        };

        // set default attributes
        root.addAttribute("name", fieldDTO.getName());
        root.addAttribute("id", fieldDTO.getName());

        // bind custom attributes
        for (FieldAttributeDTO attribute : fieldDTO.getAttributes()) {
            root.addAttribute(attribute.getKey(), attribute.getValue());
        }

        return root;
    }

    private Node createTextInput(FieldDTO fieldDTO) {
        Node input = new ElementNode(TagName.INPUT);

        input.addAttribute("type", fieldDTO.getElementType());
        input.addAttribute("placeholder", fieldDTO.getLabel());

        return input;
    }

    private Node createInputOptions(FieldDTO fieldDTO) {
        Node root = new ElementNode(TagName.DIV);

        for (FieldOptionDTO option : fieldDTO.getOptions()) {
            Node wrapper = new ElementNode(TagName.DIV);
            Node input   = new ElementNode(TagName.INPUT);

            input.addAttribute("type", fieldDTO.getElementType());
            input.addAttribute("name", fieldDTO.getName());
            input.addAttribute("value", option.getKey());

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
        return new ElementNode(TagName.TEXTAREA);
    }

    private Node createVirtual(FieldDTO fieldDTO, BuilderContext ctx) {
        Node                 node     = new ElementNode(TagName.DIV);
        Collection<FieldDTO> children = fieldDTO.getChildren().values();

        node.addAttribute("class", "input-group");
        node.addAttribute("style", "border: 1px solid red;");

        for (FieldDTO child : children) {
            node.addChild(build(child, ctx));
        }

        return node;
    }

}
