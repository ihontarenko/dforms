package df.base.html.builder.ip;

import df.base.common.elements.Node;
import df.base.common.elements.TagName;
import df.base.common.elements.node.ElementNode;
import df.base.common.elements.node.TextNode;
import df.base.common.elements.builder.ElementBuilderException;
import df.base.dto.form.FieldDTO;
import df.base.dto.form.FieldOptionDTO;
import df.base.dto.form.FormDTO;
import df.base.persistence.entity.support.ElementType;

public class Html5RenderingStrategy {

    
    public Node render(FormDTO form) {
        Node formNode = new ElementNode(TagName.FORM);

        formNode.addAttribute("method", "POST");
        formNode.addAttribute("action", "/submitForm");

        for (FieldDTO field : form.getFields().values()) {
            Node fieldNode = render(field);
            formNode.addChild(fieldNode);
        }

        return formNode;
    }

    
    public Node render(FieldDTO field) {
        Node fieldNode = switch (ElementType.valueOf(field.getElementType())) {
            case TEXT -> createTextInput(field);
            case RADIO -> createRadioOptions(field);
            case CHECKBOX -> createCheckboxOptions(field);
            case SELECT -> createSelectOptions(field);
            default -> throw new ElementBuilderException("Unsupported element type: " + field.getElementType());
        };

        render(fieldNode, field);

        return fieldNode;
    }

    
    public void render(Node element, FieldDTO field) {
        element.addAttribute("name", field.getName());
        element.addAttribute("id", field.getName());
    }

    
    public Node render(FieldOptionDTO option) {
        Node optionNode = new ElementNode(TagName.OPTION);

        optionNode.addAttribute("value", option.getKey());
        optionNode.addChild(new TextNode(option.getValue()));

        return optionNode;
    }

    private Node createTextInput(FieldDTO field) {
        Node input = new ElementNode(TagName.INPUT);

        input.addAttribute("type", "text");
        input.addAttribute("placeholder", field.getLabel());

        return input;
    }

    private Node createRadioOptions(FieldDTO field) {
        Node root = new ElementNode(TagName.DIV);

        for (FieldOptionDTO option : field.getOptions().values()) {
            Node wrapper = new ElementNode(TagName.DIV);
            Node input   = new ElementNode(TagName.INPUT);

            input.addAttribute("type", "radio");
            input.addAttribute("name", field.getName());
            input.addAttribute("value", option.getKey());

            Node label = new ElementNode(TagName.LABEL);
            label.addChild(new TextNode(option.getValue()));

            wrapper.addChild(input);
            wrapper.addChild(label);

            root.addChild(wrapper);
        }

        return root;
    }

    private Node createCheckboxOptions(FieldDTO field) {
        Node wrapper = new ElementNode(TagName.DIV);

        for (FieldOptionDTO option : field.getOptions().values()) {
            Node checkboxWrapper = new ElementNode(TagName.DIV);

            Node input = new ElementNode(TagName.INPUT);
            input.addAttribute("type", "checkbox");
            input.addAttribute("name", field.getName());
            input.addAttribute("value", option.getKey());

            Node label = new ElementNode(TagName.LABEL);
            label.addChild(new TextNode(option.getValue()));

            checkboxWrapper.addChild(input);
            checkboxWrapper.addChild(label);
            wrapper.addChild(checkboxWrapper);
        }

        return wrapper;
    }

    private Node createSelectOptions(FieldDTO field) {
        Node select = new ElementNode(TagName.SELECT);
        select.addAttribute("name", field.getName());

        for (FieldOptionDTO option : field.getOptions().values()) {
            select.addChild(render(option));
        }

        return select;
    }
}

