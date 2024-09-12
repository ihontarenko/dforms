package df.base.html.builder;

import df.base.common.elements.Node;
import df.base.common.elements.TagName;
import df.base.common.elements.node.ElementNode;
import df.base.common.elements.node.TextNode;
import df.base.common.elements.builder.ElementBuilder;
import df.base.dto.form.FieldDTO;
import df.base.dto.form.FieldOptionDTO;

import java.util.Map;

abstract public class AbstractElementBuilder implements ElementBuilder<FieldDTO, Node> {

    protected Node createCheckboxOptions(Map<String, FieldOptionDTO> options) {
        ElementNode wrapper = new ElementNode(TagName.DIV);

        for (FieldOptionDTO option : options.values()) {
            ElementNode checkboxWrapper = new ElementNode(TagName.DIV);
            ElementNode input = new ElementNode(TagName.INPUT);

            input.addAttribute("type", "checkbox");
            input.addAttribute("name", field.getName());
            input.addAttribute("value", option.getKey());

            ElementNode label = new ElementNode(TagName.LABEL);
            label.addChild(new TextNode(option.getValue()));

            checkboxWrapper.addChild(input);
            checkboxWrapper.addChild(label);
            wrapper.addChild(checkboxWrapper);
        }

        return wrapper;
    }

}
