package df.base.common.elements;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused"})

public interface Node {
    int getDepth();

    void setDepth(int depth);

    NodeType getNodeType();

    void addChild(Node child);

    void removeChild(Node child);

    List<Node> getChildren();

    void wrap(Node wrapper);

    void unwrap();

    Node getParent();

    void setParent(Node node);

    void insertBefore(Node sibling);

    void insertAfter(Node sibling);

    List<Node> getSiblings();

    TagName getTagName();

    void addAttribute(String key, String value);

    Map<String, String> getAttributes();

    String interpret(NodeContext context);
}

