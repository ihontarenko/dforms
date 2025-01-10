package df.application.pipeline.form.rendering;

import svit.dom.Node;
import svit.dom.TagName;
import svit.dom.node.HTMLElementNode;
import svit.dom.node.InputElementNode;
import svit.dom.node.TextNode;
import df.common.pipeline.context.PipelineContext;
import df.common.pipeline.PipelineProcessor;
import svit.context.ArgumentsContext;
import df.application.persistence.entity.form.Form;

public class PostBuildDemoProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        Form             entity  = arguments.requireArgument(Form.class);
        Node             root    = arguments.requireArgument(Node.class);
        InputElementNode submit  = new InputElementNode();
        HTMLElementNode  wrapper = new HTMLElementNode(TagName.DIV);

        submit.setClass("btn btn-sm btn-dark");
        submit.setType("submit");
        submit.setValue("Submit");

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

        div.setClass("alert alert-warning");
        div.append(header);
        div.append(new TextNode("The form you are viewing is displayed in DEMO mode. It is only for illustrative purposes, and no actual submission or data handling will occur."));

        return div;
    }

}
