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
import df.base.dto.reflection.MethodDTO;
import df.base.dto.reflection.MethodSetDTO;

public class ClassTypeBuilder implements NodeBuilder<ClassDTO> {

    @Override
    public Node build(ClassDTO classDTO, NodeBuilderContext ctx) {
        NodeBuilderRegistry       registry      = ctx.getRegistry();
        NodeBuilder<MethodSetDTO> methodBuilder = registry.getBuilder(MethodSetDTO.class);
        NodeBuilder<ClassSetDTO>  classBuilder  = registry.getBuilder(ClassSetDTO.class);
        Node                      wrapper       = new ElementNode(TagName.DIV);

        wrapper.append(createHeader(classDTO));
        wrapper.append(createSuperClasses(classDTO, (ClassSetBuilder) classBuilder));
        wrapper.append(methodBuilder.build(classDTO.getMethods(), ctx));

        return wrapper;
    }

    private Node createHeader(ClassDTO classDTO) {
        HTMLElementNode header  = new HTMLElementNode(TagName.H3);

        header.setClass("text-primary fw-bold");
        header.append(new TextNode(classDTO.getName()));

        return header;
    }

    private Node createSuperClasses(ClassDTO classDTO, ClassSetBuilder builder) {
        HTMLElementNode header  = new HTMLElementNode(TagName.H5);

        header.setClass("text-primary fw-bold");

        for (ClassDTO baseClass : classDTO.getBaseClasses()) {
            header.append(builder.createLinkNode(baseClass));
            header.append(new TextNode(", "));
        }

        return header;
    }

}
