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
import df.base.dto.reflection.ClassSetDTO;
import df.base.dto.reflection.MethodSetDTO;

public class ClassTypeBuilder implements NodeBuilder<ClassDTO> {

    public static final String TEXT_COMMA = ", ";

    @Override
    public Node build(ClassDTO classDTO, NodeBuilderContext ctx) {
        NodeBuilderRegistry       registry      = ctx.getRegistry();
        NodeBuilder<MethodSetDTO> methodBuilder = registry.getBuilder(MethodSetDTO.class);
        NodeBuilder<ClassSetDTO>  classBuilder  = registry.getBuilder(ClassSetDTO.class);
        Node                      wrapper       = new ElementNode(TagName.DIV);

        wrapper.append(createHeader(classDTO));
        wrapper.append(createClasses(classDTO.getBaseClasses(), (ClassSetBuilder) classBuilder, "Base Classes: "));
        wrapper.append(createClasses(classDTO.getInterfaces(), (ClassSetBuilder) classBuilder, "Interfaces: "));
        wrapper.append(methodBuilder.build(classDTO.getMethods(), ctx));

        return wrapper;
    }

    private Node createHeader(ClassDTO classDTO) {
        HTMLElementNode header  = new HTMLElementNode(TagName.H3);

        header.setClass("text-primary fw-bold");
        header.append(new TextNode(classDTO.getName()));

        return header;
    }

    private Node createClasses(ClassSetDTO classes, ClassSetBuilder builder, String label) {
        HTMLElementNode header          = new HTMLElementNode(TagName.H6);
        int             currentPosition = 0;

        header.setClass("fw-bold");

        if (classes.size() > 0) {
            header.append(new TextNode(label));
        }

        for (ClassDTO baseClass : classes) {
            header.append(builder.createLinkNode(baseClass));
            if (classes.size() > ++currentPosition) {
                header.append(new TextNode(TEXT_COMMA));
            }
        }

        if (header.hasChildren()) {
            header.prepend(new TextNode("["));
            header.append(new TextNode("]"));
            header.wrap(new ElementNode(TagName.P));
        }

        return header;
    }

}
