package df.application.html.forms.bootstrap;

import df.application.dto.form.FieldDTO;
import df.application.dto.form.FormDTO;
import org.jmouse.common.dom.Node;
import org.jmouse.common.dom.TagName;
import org.jmouse.common.dom.builder.NodeBuilder;
import org.jmouse.common.dom.builder.NodeBuilderContext;
import org.jmouse.common.dom.node.ElementNode;

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
