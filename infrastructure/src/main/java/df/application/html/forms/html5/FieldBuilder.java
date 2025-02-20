package df.application.html.forms.html5;

import df.application.dto.form.FieldDTO;
import df.application.dto.form.FieldOptionDTO;
import org.jmouse.common.dom.Node;
import org.jmouse.common.dom.TagName;
import org.jmouse.common.dom.builder.NodeBuilder;
import org.jmouse.common.dom.builder.NodeBuilderContext;
import org.jmouse.common.dom.node.ElementNode;
import org.jmouse.common.dom.node.TextNode;
import df.application.persistence.entity.support.ElementType;

public class FieldBuilder implements NodeBuilder<FieldDTO> {

    @Override
    public Node build(FieldDTO fieldDTO, NodeBuilderContext ctx) {
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
        root.append(new TextNode(option.getValue()));

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
            label.append(new TextNode(option.getValue()));

            wrapper.append(input);
            wrapper.append(label);

            root.append(wrapper);
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
            label.append(new TextNode(option.getValue()));

            checkboxWrapper.append(input);
            checkboxWrapper.append(label);
            wrapper.append(checkboxWrapper);
        }

        return wrapper;
    }

    private Node createSelectOptions(FieldDTO fieldDTO) {
        Node select = new ElementNode(TagName.SELECT);
        select.addAttribute("name", fieldDTO.getName());

        for (FieldOptionDTO option : fieldDTO.getOptions()) {
            select.append(render(option));
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
