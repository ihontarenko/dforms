package df.base.html.forms.bootstrap;

import df.base.common.elements.Node;
import df.base.common.elements.PostDataProvider;
import df.base.common.elements.TagName;
import df.base.common.elements.builder.NodeBuilder;
import df.base.common.elements.builder.NodeBuilderContext;
import df.base.common.elements.node.ElementNode;
import df.base.common.elements.node.TextNode;
import df.base.common.libs.jbm.StringUtils;
import df.base.dto.form.FieldAttributeDTO;
import df.base.dto.form.FieldDTO;
import df.base.dto.form.FieldOptionDTO;

import java.util.Collection;
import java.util.List;

import static df.base.persistence.entity.support.ElementType.valueOf;
import static java.util.stream.Collectors.joining;

public class FieldBuilder implements NodeBuilder<FieldDTO> {

    @Override
    public Node build(FieldDTO fieldDTO, NodeBuilderContext ctx) {
        return switch (valueOf(fieldDTO.getElementType())) {
            case TEXT, NUMBER, URL, EMAIL, DATE -> createTextInput(fieldDTO, ctx);
            case SELECT -> createSelectOptions(fieldDTO, ctx);
            case TEXTAREA -> createTextarea(fieldDTO, ctx);
            case CHECKBOX, RADIO -> createInputOptions(fieldDTO, ctx);
            case NONE -> createVirtual(fieldDTO, ctx);
        };
    }

    private void addElementAttributes(Node node, List<FieldAttributeDTO> attributes) {
        for (FieldAttributeDTO attribute : attributes) {
            node.addAttribute(attribute.getKey(), attribute.getValue());
        }
    }

    private Node createTextInput(FieldDTO fieldDTO, NodeBuilderContext ctx) {
        Node root  = new ElementNode(TagName.DIV);
        Node input = new ElementNode(TagName.INPUT);
        Node label = new ElementNode(TagName.LABEL);
        Node small = new ElementNode(TagName.SMALL);

        PostDataProvider dataProvider = ctx.getDataProvider();

        label.append(new TextNode(fieldDTO.getLabel()));
        label.addAttribute("for", fieldDTO.id());
        label.addAttribute("class", "form-label");

        input.addAttribute("type", fieldDTO.getElementType());
        input.addAttribute("placeholder", fieldDTO.getLabel());
        input.addAttribute("id", fieldDTO.id());
        input.addAttribute("class", "form-control");
        input.addAttribute("name", fieldDTO.getName());

        addElementAttributes(input, fieldDTO.getAttributes());

        Object value = dataProvider.getValue(fieldDTO.getName());

        if (value != null) {
            input.addAttribute("value", (String) value);
        }

        root.append(label);
        root.append(input);

        if (dataProvider.hasError(fieldDTO.getName())) {
            input.addAttribute("class", "%s is-invalid".formatted(input.getAttribute("class")));
            root.append(createInvalidFeedback(dataProvider.getError(fieldDTO.getName()).message()));
        }

        if (StringUtils.hasText(fieldDTO.getDescription())) {
            small.append(new TextNode(fieldDTO.getDescription()));
            root.append(small);
        }

        return root;
    }

    private Node createInputOptions(FieldDTO fieldDTO, NodeBuilderContext ctx) {
        Node root = new ElementNode(TagName.DIV);

        for (FieldOptionDTO option : fieldDTO.getOptions()) {
            Node wrapper = new ElementNode(TagName.DIV);
            Node input   = new ElementNode(TagName.INPUT);

            input.addAttribute("type", fieldDTO.getElementType());
            input.addAttribute("name", fieldDTO.getName());
            input.addAttribute("value", option.getKey());

            addElementAttributes(input, fieldDTO.getAttributes());

            Node label = new ElementNode(TagName.LABEL);

            label.append(new TextNode(option.getValue()));

            wrapper.append(input);
            wrapper.append(label);

            root.append(wrapper);
        }

        return root;
    }

    private Node createSelectOptions(FieldDTO fieldDTO, NodeBuilderContext ctx) {
        Node select = new ElementNode(TagName.SELECT);

        select.addAttribute("name", fieldDTO.getName());
        addElementAttributes(select, fieldDTO.getAttributes());

        for (FieldOptionDTO option : fieldDTO.getOptions()) {
            select.append(createSelectOption(option, ctx));
        }

        return select;
    }

    public Node createSelectOption(FieldOptionDTO option, NodeBuilderContext ctx) {
        Node root = new ElementNode(TagName.OPTION);

        root.addAttribute("value", option.getKey());
        root.append(new TextNode(option.getValue()));

        return root;
    }

    private Node createTextarea(FieldDTO fieldDTO, NodeBuilderContext ctx) {
        Node textarea = new ElementNode(TagName.TEXTAREA);

        textarea.addAttribute("name", fieldDTO.getName());
        addElementAttributes(textarea, fieldDTO.getAttributes());

        return textarea;
    }

    private Node createVirtual(FieldDTO fieldDTO, NodeBuilderContext ctx) {
        Node                 input       = new ElementNode(TagName.INPUT);
        Node                 node        = new ElementNode(TagName.DIV);
        Collection<FieldDTO> children    = fieldDTO.getChildren().values();
        String               virtualName = children.stream().map(FieldDTO::getName).collect(joining("_"));

        addElementAttributes(node, fieldDTO.getAttributes());

        input.addAttribute("name", virtualName);
        input.addAttribute("type", "hidden");

        for (FieldDTO child : children) {
            node.append(build(child, ctx));
        }

        node.append(input);

        return node;
    }

    private Node createInvalidFeedback(String message) {
        Node feedback = new ElementNode(TagName.DIV);

        feedback.addAttribute("class", "invalid-feedback");
        feedback.append(new TextNode(message));

        return feedback;
    }

}
