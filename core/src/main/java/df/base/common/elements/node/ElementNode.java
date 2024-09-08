package df.base.common.elements.node;

import df.base.common.elements.NodeType;
import df.base.common.elements.TagName;

public class ElementNode extends AbstractNode {

    public ElementNode(TagName tagName) {
        super(NodeType.ELEMENT, tagName);
    }

}
