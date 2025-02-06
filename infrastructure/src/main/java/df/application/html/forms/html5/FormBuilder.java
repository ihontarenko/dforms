package df.application.html.forms.html5;

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
        Collection<FieldDTO> fields = formDTO.getFields();
        Node                 root   = new ElementNode(TagName.FORM);

        // todo: apply parser with config
        root.addAttribute("method", "POST");
        root.addAttribute("action", "/submitForm");

        for (FieldDTO field : fields) {
            NodeBuilder<FieldDTO> builder = ctx.getRegistry().getBuilder(FieldDTO.class);
            root.append(builder.build(field, ctx));
        }

        return root;
    }

}
