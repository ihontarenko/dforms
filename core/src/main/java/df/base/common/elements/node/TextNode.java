package df.base.common.elements.node;

import df.base.common.elements.NodeType;

public class TextNode extends NonElementNode {

    private String text;

    public TextNode() {
        this(null);
    }

    public TextNode(String text) {
        super(NodeType.TEXT);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
