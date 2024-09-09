package df.base.common.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NodeContext {

    public static final Consumer<Node>  REORDER_EXECUTOR;

    static {
        REORDER_EXECUTOR = node -> node.setDepth(node.hasParent() ? node.getParent().getDepth() + 1 : 0);
    }
    private final       RendererFactory rendererFactory;
    private final       List<Interceptor> interceptors = new ArrayList<>();

    public NodeContext(RendererFactory rendererFactory, List<Interceptor> interceptors) {
        this.rendererFactory = rendererFactory;
        this.interceptors.addAll(interceptors);
    }

    public RendererFactory getRendererFactory() {
        return rendererFactory;
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    public void invokeBeforeRender(Node node) {
        for (Interceptor interceptor : interceptors) {
            interceptor.beforeRender(node);
        }
    }

    public void invokeAfterRender(Node node) {
        for (Interceptor interceptor : interceptors) {
            interceptor.afterRender(node);
        }
    }

    public void invokeOnError(Node node, Exception e) {
        for (Interceptor interceptor : interceptors) {
            interceptor.onRenderError(node, e);
        }
    }
}
