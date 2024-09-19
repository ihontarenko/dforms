package df.base.pipeline.form_render;

import df.base.common.elements.Node;
import df.base.common.elements.TagName;
import df.base.common.elements.node.HTMLElementNode;
import df.base.common.elements.node.InputElementNode;
import df.base.common.elements.node.TextNode;
import df.base.common.pipeline.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.PipelineArguments;
import df.base.persistence.entity.form.Form;

public class PostBuildDemoProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, PipelineArguments arguments) throws Exception {
        Form             entity  = arguments.requireArgument(Form.class);
        Node             root    = arguments.requireArgument(Node.class);
        InputElementNode submit  = new InputElementNode();
        HTMLElementNode  wrapper = new HTMLElementNode(TagName.DIV);

        submit.setClass("btn btn-sm btn-dark");
        submit.setType("submit");
        submit.setValue("Submit Demo!");

        wrapper.setClass("mt-4");
        wrapper.append(submit);

        root.prepend(createInformationBlock());
        root.append(wrapper);

        root.addAttribute("action", "/form/%s/demo".formatted(entity.getId()));
        root.addAttribute("method", "post");

        return FormRenderReturnCode.RENDER_NOTE_TREE;
    }

    private Node createInformationBlock() {
        HTMLElementNode div = new HTMLElementNode(TagName.DIV);
        HTMLElementNode header = new HTMLElementNode(TagName.H4);

        header.append(new TextNode("Demo Mode Notice"));

        div.setClass("alert alert-danger");
        div.append(header);
        div.append(new TextNode("The form you are viewing is displayed in DEMO mode. It is only for illustrative purposes, and no actual submission or data handling will occur."));

        return div;
    }

}
