package df.common.elements.renderer;

import df.common.elements.Node;
import df.common.elements.NodeContext;
import df.common.elements.Renderer;
import df.common.elements.node.CommentNode;

public class CommentRenderer implements Renderer {

    @Override
    public String render(Node node, NodeContext context) {
        if (node instanceof CommentNode commentNode) {
            return indentation(node.getDepth()) + "<!-- " + commentNode.getComment() + " -->\n";
        }

        throw new IllegalArgumentException("INCORRECT NODE TYPE FOR RENDERER");
    }

}
