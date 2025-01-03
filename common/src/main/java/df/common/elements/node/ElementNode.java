package df.common.elements.node;

import df.common.elements.NodeType;
import df.common.elements.TagName;

public class ElementNode extends AbstractNode {

    public ElementNode(TagName tagName) {
        super(NodeType.ELEMENT, tagName);
    }

}
