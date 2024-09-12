package df.base.html.builder.html5;

import df.base.common.elements.Node;
import df.base.common.elements.TagName;
import df.base.common.elements.builder.Builder;
import df.base.common.elements.builder.BuilderContext;
import df.base.common.elements.node.ElementNode;
import df.base.common.elements.node.TextNode;
import df.base.dto.form.FieldDTO;
import df.base.dto.form.FieldOptionDTO;
import df.base.persistence.entity.support.ElementType;

public class FieldBuilder implements Builder<FieldDTO> {

    @Override
    public Node build(FieldDTO fieldDTO, BuilderContext ctx) {
        ElementType elementType = ElementType.valueOf(fieldDTO.getElementType());

        Node root = switch (elementType) {
            case TEXT, NUMBER, URL, EMAIL, DATE -> createTextInput(fieldDTO);
            case RADIO -> createRadioOptions(fieldDTO);
            case CHECKBOX -> createCheckboxOptions(fieldDTO);
            case SELECT -> createSelectOptions(fieldDTO);
            case TEXTAREA -> createTextare(fieldDTO);
            case NONE -> createVirtual(fieldDTO);
        };

        // set default attributes
        root.addAttribute("name", fieldDTO.getName());
        root.addAttribute("id", fieldDTO.getName());

        return root;
    }

    public void render(Node element, FieldDTO fieldDTO) {
        element.addAttribute("name", fieldDTO.getName());
        element.addAttribute("id", fieldDTO.getName());
    }


    public Node render(FieldOptionDTO option) {
        Node root = new ElementNode(TagName.OPTION);

        root.addAttribute("value", option.getKey());
        root.addChild(new TextNode(option.getValue()));

        return root;
    }

    private Node createTextInput(FieldDTO fieldDTO) {
        Node input = new ElementNode(TagName.INPUT);

        input.addAttribute("type", "text");
        input.addAttribute("placeholder", fieldDTO.getLabel());

        return input;
    }

    private Node createRadioOptions(FieldDTO fieldDTO) {
        Node root = new ElementNode(TagName.DIV);

        for (FieldOptionDTO option : fieldDTO.getOptions()) {
            Node wrapper = new ElementNode(TagName.DIV);
            Node input   = new ElementNode(TagName.INPUT);

            input.addAttribute("type", "radio");
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

    private Node createCheckboxOptions(FieldDTO fieldDTO) {
        Node wrapper = new ElementNode(TagName.DIV);

        for (FieldOptionDTO option : fieldDTO.getOptions()) {
            Node checkboxWrapper = new ElementNode(TagName.DIV);

            Node input = new ElementNode(TagName.INPUT);
            input.addAttribute("type", "checkbox");
            input.addAttribute("name", fieldDTO.getName());
            input.addAttribute("value", option.getKey());

            Node label = new ElementNode(TagName.LABEL);
            label.addChild(new TextNode(option.getValue()));

            checkboxWrapper.addChild(input);
            checkboxWrapper.addChild(label);
            wrapper.addChild(checkboxWrapper);
        }

        return wrapper;
    }

    private Node createSelectOptions(FieldDTO fieldDTO) {
        Node select = new ElementNode(TagName.SELECT);
        select.addAttribute("name", fieldDTO.getName());

        for (FieldOptionDTO option : fieldDTO.getOptions()) {
            select.addChild(render(option));
        }

        return select;
    }

    private Node createTextare(FieldDTO fieldDTO) {
        return new ElementNode(TagName.TEXTAREA);
    }

    private Node createVirtual(FieldDTO fieldDTO) {
        return new TextNode("NO IMPLEMENTATION FOR VIRTUAL COLUMN YET");
    }

}
