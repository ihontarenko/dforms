package df.base.html.builder.html5;

import df.base.common.elements.Node;
import df.base.common.elements.TagName;
import df.base.common.elements.builder.Builder;
import df.base.common.elements.builder.BuilderContext;
import df.base.common.elements.node.ElementNode;
import df.base.dto.form.FieldDTO;
import df.base.dto.form.FormDTO;

import java.util.Collection;

public class FormBuilder implements Builder<FormDTO> {

    @Override
    public Node build(FormDTO formDTO, BuilderContext ctx) {
        Collection<FieldDTO> fields = formDTO.getFields();
        Node                 root   = new ElementNode(TagName.FORM);

        // todo: apply parser with config
        root.addAttribute("method", "POST");
        root.addAttribute("action", "/submitForm");

        for (FieldDTO field : fields) {
            Builder<FieldDTO> builder = ctx.getStrategy().getBuilder(FieldDTO.class);
            root.addChild(builder.build(field, ctx));
        }

        return root;
    }

}
