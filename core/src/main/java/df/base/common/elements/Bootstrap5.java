package df.base.common.elements;

import df.base.common.elements.node.ElementNode;

public class Bootstrap5 {

    public void apply(NodeContext context) {
        context.addInterceptor(new BootstrapInputWrapperInterceptor());
        context.addInterceptor(new BootstrapContainerInterceptor());
    }

    private static class BootstrapInputWrapperInterceptor implements Interceptor {

        @Override
        public void beforeRender(Node node) {
            if (node.getTagName() == TagName.INPUT) {
                Node parent = node.getParent();
                if (parent == null || !parent.getAttributes().getOrDefault("class", "").contains("form-group")) {
                    ElementNode wrapper = new ElementNode(TagName.DIV);
                    wrapper.addAttribute("class", "form-group");
                    node.wrap(wrapper);
                }
            }
        }

        @Override
        public void afterRender(Node node) {

        }

        @Override
        public void onRenderError(Node node, Exception e) {
            System.err.println("BOOTSTRAP INPUT WRAPPER ERROR: " + e.getMessage());
        }

    }

    private static class BootstrapContainerInterceptor implements Interceptor {

        @Override
        public void beforeRender(Node node) {
            if (node.getTagName() == TagName.DIV) {
                String existingClasses = node.getAttributes().getOrDefault("class", "");
                if (!existingClasses.contains("container")) {
                    node.addAttribute("class", existingClasses + " container");
                }
            }
        }

        @Override
        public void afterRender(Node node) {
        }

        @Override
        public void onRenderError(Node node, Exception e) {
            System.err.println("BOOTSTRAP CONTAINER ERROR: " + e.getMessage());
        }

    }
}

