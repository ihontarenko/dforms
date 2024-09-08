package df.base.common.elements.renderer;

import df.base.common.elements.Node;
import df.base.common.elements.NodeContext;
import df.base.common.elements.NodeType;
import df.base.common.elements.Renderer;

import java.util.Map;

public class ElementRenderer implements Renderer {

    @Override
    public String render(Node node, NodeContext context) {
        if (node.getNodeType() == NodeType.ELEMENT) {
            StringBuilder html = new StringBuilder();

            html.append("<").append(node.getTagName());

            Map<String, String> attributes = node.getAttributes();
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                html.append(" ").append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
            }

            html.append(">");

            for (Node child : node.getChildren()) {
                html.append(child.interpret(context));
            }

            html.append("</").append(node.getTagName()).append(">");

            return html.toString();
        }

        throw new IllegalArgumentException("INCORRECT NODE TYPE FOR RENDERER");
    }
}
