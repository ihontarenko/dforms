package df.base.common.elements;

import java.util.ArrayList;
import java.util.List;

public class NodeContext {
    private final RendererFactory   rendererFactory;
    private final List<Interceptor> interceptors = new ArrayList<>();

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
