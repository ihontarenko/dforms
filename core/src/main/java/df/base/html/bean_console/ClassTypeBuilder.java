package df.base.html.bean_console;

import df.base.common.elements.Node;
import df.base.common.elements.TagName;
import df.base.common.elements.builder.NodeBuilder;
import df.base.common.elements.builder.NodeBuilderContext;
import df.base.common.elements.builder.NodeBuilderRegistry;
import df.base.common.elements.node.ElementNode;
import df.base.common.elements.node.HTMLElementNode;
import df.base.common.elements.node.TextNode;
import df.base.dto.reflection.ClassDTO;
import df.base.dto.reflection.MethodSetDTO;

public class ClassTypeBuilder implements NodeBuilder<ClassDTO> {

    @Override
    public Node build(ClassDTO classDTO, NodeBuilderContext ctx) {
        NodeBuilderRegistry registry = ctx.getRegistry();
        Node                wrapper  = new ElementNode(TagName.DIV);

        wrapper.append(createHeader(classDTO));
        wrapper.append(registry.getBuilder(MethodSetDTO.class).build(classDTO.getMethods(), ctx));

        return wrapper;
    }

    private Node createHeader(ClassDTO classDTO) {
        HTMLElementNode header  = new HTMLElementNode(TagName.H3);

        header.setClass("text-primary fw-bold");
        header.append(new TextNode(classDTO.getShortName()));

        return header;
    }

}
