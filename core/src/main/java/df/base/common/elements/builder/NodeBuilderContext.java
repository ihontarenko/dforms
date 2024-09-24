package df.base.common.elements.builder;

import df.base.common.context.AbstractAttributesContext;
import df.base.common.elements.PostDataProvider;

public class NodeBuilderContext extends AbstractAttributesContext {

    public PostDataProvider getDataProvider() {
        return this.requireAttribute(PostDataProvider.class);
    }

    public void setDataProvider(PostDataProvider dataProvider) {
        setAttribute(PostDataProvider.class, dataProvider);
    }

    public NodeBuilderStrategy getStrategy() {
        return this.requireAttribute(NodeBuilderStrategy.class);
    }

    public void setStrategy(NodeBuilderStrategy strategy) {
        this.setAttribute(NodeBuilderStrategy.class, strategy);
    }

}
