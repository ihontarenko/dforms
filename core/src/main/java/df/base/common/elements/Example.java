package df.base.common.elements;

import df.base.common.elements.interceptor.LoggingInterceptor;
import df.base.common.elements.node.CommentNode;
import df.base.common.elements.node.ElementNode;
import df.base.common.elements.node.TextNode;

import java.util.List;

public class Example {

    public static void main(String[] args) {
        RendererFactory factory = new RendererFactory();
        Interceptor     interceptor     = new LoggingInterceptor();
        NodeContext     context         = new NodeContext(factory, List.of(interceptor));

        ElementNode html = new ElementNode(TagName.HTML);
        ElementNode body = new ElementNode(TagName.BODY);
        ElementNode div = new ElementNode(TagName.DIV);
        ElementNode p = new ElementNode(TagName.P);

        div.addAttribute("class", "container");

        CommentNode comment = new CommentNode("This is a comment");

        TextNode text = new TextNode("Hello, World!");

        div.addChild(p);
        p.addChild(text);
        body.addChild(div);
        body.addChild(comment);
        html.addChild(body);


        html.execute(node -> node.insertBefore(new CommentNode(node.getTagName() + ":" + node.getNodeType())));

        // after finish build html-tree reorder depth
        html.execute(node -> node.setDepth(node.hasParent() ? node.getParent().getDepth() + 1 : 0));

        String output = html.interpret(context);
        System.out.println(output);
    }

}
