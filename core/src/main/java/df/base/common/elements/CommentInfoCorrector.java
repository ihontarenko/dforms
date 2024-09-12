package df.base.common.elements;

import df.base.common.elements.node.CommentNode;

import java.time.Instant;

public class CommentInfoCorrector implements Corrector{

    @Override
    public void accept(Node node) {
        node.insertBefore(new CommentNode("OPEN: [%s]".formatted(node)));
        node.insertAfter(new CommentNode("CLOSE: [%s]".formatted(node)));
    }

}
