package df.base.common.elements;

public interface Interceptor {

    void beforeRender(Node node);

    void afterRender(Node node);

    void onRenderError(Node node, Exception e);

}
