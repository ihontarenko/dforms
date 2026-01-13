package df.application.pipeline.form.rendering;

import org.jmouse.common.dom.Node;
import org.jmouse.common.dom.TagName;
import org.jmouse.common.dom.node.HTMLElementNode;
import org.jmouse.common.dom.node.InputElementNode;
import org.jmouse.common.dom.node.TextNode;
import org.jmouse.common.pipeline.context.PipelineContext;
import org.jmouse.common.pipeline.PipelineProcessor;
import org.jmouse.core.context.ArgumentsContext;
import df.application.persistence.entity.form.Form;

public class PostBuildDemoProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        Form             entity  = arguments.getRequiredArgument(Form.class);
        Node             root    = arguments.getRequiredArgument(Node.class);
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
