package df.base.common.elements;

import df.base.common.elements.node.CommentNode;

public class Cleanup {

    public void apply(NodeContext context) {
        context.addInterceptor(new DoubleWrapperCleanupInterceptor());
        context.addInterceptor(new CommentAdderInterceptor());
    }

    // Інтерцептор для розгортання подвійно обгорнутих елементів
    private static class DoubleWrapperCleanupInterceptor implements Interceptor {

        @Override
        public void beforeRender(Node node) {
            Node parent = node.getParent();
            if (parent != null && parent.getTagName() == TagName.DIV && node.getTagName() == TagName.DIV) {
                node.unwrap();
            }
        }

        @Override
        public void afterRender(Node node) {
        }

        @Override
        public void onRenderError(Node node, Exception e) {
            System.err.println("ERROR DURING DOUBLE WRAPPER CLEANUP: " + e.getMessage());
        }
    }

    // Інтерцептор для додавання коментарів над кожним HTML тегом
    private static class CommentAdderInterceptor implements Interceptor {

        @Override
        public void beforeRender(Node element) {
                CommentNode comment = new CommentNode("<!-- " + element.getTagName() + ": " +
                        element.getAttributes().getOrDefault("class", "") + " -->");
                element.insertBefore(comment);
        }

        @Override
        public void afterRender(Node node) {
        }

        @Override
        public void onRenderError(Node node, Exception e) {
            System.err.println("ERROR DURING COMMENT ADDITION: " + e.getMessage());
        }

    }
}
