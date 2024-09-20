package df.base.common.elements.builder;

import df.base.common.context.AbstractAttributesContext;

import static java.util.Objects.requireNonNull;

public class NodeBuilderContext extends AbstractAttributesContext {

    public NodeBuilderStrategy getStrategy() {
        return requireNonNull(this.getAttribute(NodeBuilderStrategy.class));
    }

    public void setStrategy(NodeBuilderStrategy strategy) {
        this.setAttribute(NodeBuilderStrategy.class, strategy);
    }

}
