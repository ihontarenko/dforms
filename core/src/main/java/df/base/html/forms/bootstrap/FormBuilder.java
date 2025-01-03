package df.base.html.forms.bootstrap;

import df.base.common.elements.Node;
import df.base.common.elements.TagName;
import df.base.common.elements.builder.NodeBuilder;
import df.base.common.elements.builder.NodeBuilderContext;
import df.base.common.elements.node.ElementNode;
import df.base.dto.form.FieldDTO;
import df.base.dto.form.FormDTO;

import java.util.Collection;

public class FormBuilder implements NodeBuilder<FormDTO> {

    @Override
    public Node build(FormDTO formDTO, NodeBuilderContext ctx) {
        Collection<FieldDTO> fields  = formDTO.getFields();
        Node                 root    = new ElementNode(TagName.FORM);
        Node                 wrapper = new ElementNode(TagName.FIELDSET);

        root.append(wrapper);

        for (FieldDTO field : fields) {
            var  builder = ctx.getRegistry().getBuilder(FieldDTO.class);
            Node node    = builder.build(field, ctx);
            wrapper.append(node);
            node.addAttribute("class", "mt-4");
        }

        return root;
    }

}
