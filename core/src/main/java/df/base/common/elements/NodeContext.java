package df.base.common.elements;

public class NodeContext {

    public static final ReorderNodeCorrector REORDER_NODE_CORRECTOR;
    public static final CommentInfoCorrector COMMENT_INFO_CORRECTOR;

    static {
        REORDER_NODE_CORRECTOR = new ReorderNodeCorrector();
        COMMENT_INFO_CORRECTOR = new CommentInfoCorrector();
    }

    private final RendererFactory rendererFactory;

    public NodeContext(RendererFactory rendererFactory) {
        this.rendererFactory = rendererFactory;
    }

    public RendererFactory getRendererFactory() {
        return rendererFactory;
    }

}
