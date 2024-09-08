package df.base.common.elements.interceptor;

import df.base.common.elements.Interceptor;
import df.base.common.elements.Node;

public class LoggingInterceptor implements Interceptor {

    @Override
    public void beforeRender(Node node) {
        System.out.println("BEFORE RENDERING: " + node);
    }

    @Override
    public void afterRender(Node node) {
        System.out.println("AFTER RENDERING: " + node);
    }

    @Override
    public void onRenderError(Node node, Exception e) {
        System.err.println("ERROR DURING RENDERING NODE: " + node + ", error: " + e.getMessage());
    }

}

