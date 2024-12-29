package df.base.common.elements.renderer;

import df.base.common.elements.Node;
import df.base.common.elements.NodeContext;
import df.base.common.elements.Renderer;
import df.base.common.elements.node.TextNode;

public class TextRenderer implements Renderer {

    @Override
    public String render(Node node, NodeContext context) {
        if (node instanceof TextNode textNode) {
            return indentation(node.getDepth()) + textNode.getText() + "\n";
        }

        throw new IllegalArgumentException("Incorrect node type for renderer");
    }

}
