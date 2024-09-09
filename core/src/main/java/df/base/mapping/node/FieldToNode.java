package df.base.mapping.node;

import df.base.common.Mapper;
import df.base.common.elements.Node;
import df.base.common.elements.NodeContext;
import df.base.common.elements.TagName;
import df.base.common.elements.node.ElementNode;
import df.base.common.elements.node.TextNode;
import df.base.persistence.entity.form.Field;
import df.base.persistence.entity.form.FieldAttribute;
import org.springframework.stereotype.Component;

@Component
public class FieldToNode implements Mapper<Field, String> {

    private final NodeContext context;

    public FieldToNode(NodeContext context) {
        this.context = context;
    }

    @Override
    public String map(Field source) {
        Node root  = new ElementNode(TagName.DIV);
        Node field = new ElementNode(TagName.INPUT);
        Node label = new ElementNode(TagName.LABEL);

        field.addAttribute("name", source.getName());
        field.addAttribute("id", source.getId());
        field.addAttribute("type", source.getElementType().name());

        label.addChild(new TextNode(source.getLabel()));

        for (FieldAttribute attribute : source.getAttributes()) {
            field.addAttribute(attribute.getName(), attribute.getValue());
        }

        root.addChild(label);
        root.addChild(field);

        root.addAttribute("class", "form-group");

        root.execute(NodeContext.REORDER_EXECUTOR);

        return root.interpret(context);
    }

    @Override
    public Field reverse(String source) {
        throw new UnsupportedOperationException();
    }

}
