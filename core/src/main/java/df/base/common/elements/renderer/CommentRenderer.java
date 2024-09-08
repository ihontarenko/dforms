package df.base.common.elements.renderer;

import df.base.common.elements.Node;
import df.base.common.elements.NodeContext;
import df.base.common.elements.Renderer;
import df.base.common.elements.node.CommentNode;

public class CommentRenderer implements Renderer {

    @Override
    public String render(Node node, NodeContext context) {
        if (node instanceof CommentNode commentNode) {
            return "<!-- " + commentNode.getComment() + " -->";
        }

        throw new IllegalArgumentException("INCORRECT NODE TYPE FOR RENDERER");
    }

}
