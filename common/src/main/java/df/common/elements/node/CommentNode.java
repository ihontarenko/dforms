package df.common.elements.node;

import df.common.elements.NodeType;

public class CommentNode extends AbstractNode {

    private final TextNode comment = new TextNode();

    public CommentNode(String comment) {
        super(NodeType.COMMENT, null);
        this.comment.setText(comment);
    }

    public CommentNode() {
        this(null);
    }

    public String getComment() {
        return comment.getText();
    }

    public void setComment(String comment) {
        this.comment.setText(comment);
    }

}
