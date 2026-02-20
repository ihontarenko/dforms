package df.application.html.bean_console;

import org.jmouse.common.dom.Node;
import org.jmouse.common.dom.TagName;
import org.jmouse.common.dom.old_builder.NodeBuilder;
import org.jmouse.common.dom.old_builder.NodeBuilderContext;
import org.jmouse.common.dom.old_builder.NodeBuilderRegistry;
import org.jmouse.common.dom.node.ElementNode;
import org.jmouse.common.dom.node.HTMLElementNode;
import org.jmouse.common.dom.node.TextNode;
import df.application.dto.reflection.ClassDTO;
import df.application.dto.reflection.ClassListDTO;
import df.application.dto.reflection.MethodListDTO;

public class ClassTypeBuilder implements NodeBuilder<ClassDTO> {

    public static final String TEXT_COMMA = ", ";

    @Override
    public Node build(ClassDTO classDTO, NodeBuilderContext ctx) {
        NodeBuilderRegistry        registry      = ctx.getRegistry();
        NodeBuilder<MethodListDTO> methodBuilder = registry.getBuilder(MethodListDTO.class);
        NodeBuilder<ClassListDTO>  classBuilder  = registry.getBuilder(ClassListDTO.class);
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

    private Node createClasses(ClassListDTO classes, ClassSetBuilder builder, String label) {
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
