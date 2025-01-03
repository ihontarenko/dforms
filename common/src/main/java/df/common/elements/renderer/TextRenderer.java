package df.common.elements.renderer;

import df.common.elements.Node;
import df.common.elements.NodeContext;
import df.common.elements.Renderer;
import df.common.elements.node.TextNode;

public class TextRenderer implements Renderer {

    @Override
    public String render(Node node, NodeContext context) {
        if (node instanceof TextNode textNode) {
            return indentation(node.getDepth()) + textNode.getText() + "\n";
        }

        throw new IllegalArgumentException("Incorrect node type for renderer");
    }

}
