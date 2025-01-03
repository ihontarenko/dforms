package df.application.html.forms.html5;

import df.application.dto.form.FieldDTO;
import df.application.dto.form.FormDTO;
import df.common.elements.Node;
import df.common.elements.TagName;
import df.common.elements.builder.NodeBuilder;
import df.common.elements.builder.NodeBuilderContext;
import df.common.elements.node.ElementNode;

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
