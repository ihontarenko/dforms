package df.base.common.elements.node;

import df.base.common.elements.*;

import java.util.*;

abstract public class AbstractNode implements Node {

    protected TagName             tagName;
    protected NodeType            nodeType;
    protected List<Node>          children   = new ArrayList<>();
    protected Map<String, String> attributes = new HashMap<>();
    protected int                 depth;
    protected Node                parent;

    public AbstractNode(NodeType nodeType, TagName tagName) {
        this.tagName = tagName;
        this.depth = 0;
        this.nodeType = nodeType;
    }

    @Override
    public int getDepth() {
        return depth;
    }

    @Override
    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public void addChild(Node child) {
        child.setDepth(this.depth + 1);
        child.setParent(this);
        children.add(child);
    }

    @Override
    public void removeChild(Node child) {
        children.remove(child);
    }

    @Override
    public List<Node> getChildren() {
        return children;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public NodeType getNodeType() {
        return nodeType;
    }

    @Override
    public TagName getTagName() {
        return tagName;
    }

    @Override
    public void addAttribute(String key, String value) {
        attributes.put(key, value);
    }

    @Override
    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public void wrap(Node wrapper) {
        if (parent != null) {
            int index = parent.getChildren().indexOf(this);
            if (index != -1) {
                wrapper.setParent(parent);
                parent.getChildren().set(index, wrapper);
                wrapper.addChild(this);
                setParent(wrapper);
                wrapper.setDepth(parent.getDepth() + 1);
            }
        }
    }

    @Override
    public void unwrap() {
        if (parent != null) {
            Node grandParent = parent.getParent();
            if (grandParent != null) {
                int index = grandParent.getChildren().indexOf(parent);
                if (index != -1) {
                    grandParent.getChildren().set(index, this);
                    setParent(grandParent);
                    setDepth(grandParent.getDepth() + 1);
                }
            }
        }
    }

    @Override
    public void insertBefore(Node sibling) {
        if (parent != null) {
            int index = parent.getChildren().indexOf(this);
            if (index != -1) {
                parent.getChildren().add(index, sibling);
                sibling.setParent(parent);
                sibling.setDepth(parent.getDepth() + 1);
            }
        }
    }

    @Override
    public void insertAfter(Node sibling) {
        if (parent != null) {
            int index = parent.getChildren().indexOf(this);
            if (index != -1 && index < parent.getChildren().size()) {
                parent.getChildren().add(index + 1, sibling);
                sibling.setParent(parent);
                sibling.setDepth(parent.getDepth() + 1);
            }
        }
    }

    @Override
    public List<Node> getSiblings() {
        List<Node> siblings = new ArrayList<>();

        if (parent != null) {
            List<Node> parentChildren = parent.getChildren();
            int        currentIndex   = parentChildren.indexOf(this);

            if (currentIndex > 0) {
                siblings.add(parentChildren.get(currentIndex - 1));
            } else {
                siblings.add(null);
            }

            if (currentIndex < parentChildren.size() - 1) {
                siblings.add(parentChildren.get(currentIndex + 1));
            } else {
                siblings.add(null);
            }
        }

        return siblings;
    }

    @Override
    public String interpret(NodeContext context) {
        try {
            context.invokeBeforeRender(this);

            Renderer renderer = context.getRendererFactory().getRenderer(this);
            String   result   = renderer.render(this, context);

            context.invokeAfterRender(this);

            return result;
        } catch (Exception e) {
            context.invokeOnError(this, e);
            throw new RuntimeException("Error during rendering", e);
        }
    }

    @Override
    public String toString() {
        return "%s%s_NODE".formatted(tagName == null ? "" : tagName.name().toUpperCase() + "_", nodeType);
    }
}
