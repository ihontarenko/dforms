package df.application.html.bean_console;

import org.jmouse.common.dom.Node;
import org.jmouse.common.dom.TagName;
import org.jmouse.common.dom.old_builder.NodeBuilder;
import org.jmouse.common.dom.old_builder.NodeBuilderContext;
import org.jmouse.common.dom.node.ElementNode;
import org.jmouse.common.dom.node.TextNode;
import df.common.flow.Flow;
import org.jmouse.core.reflection.Reflections;
import df.application.dto.reflection.ClassDTO;
import df.application.dto.reflection.ClassListDTO;
import df.application.dto.reflection.MethodDTO;
import df.application.dto.reflection.MethodListDTO;

import java.util.List;
import java.util.Map;

public class MethodSetBuilder implements NodeBuilder<MethodListDTO> {

    @Override
    public Node build(MethodListDTO methodDTOs, NodeBuilderContext ctx) {
        Node            wrapper = new ElementNode(TagName.DIV);
        ClassSetBuilder builder = (ClassSetBuilder) ctx.getRegistry().getBuilder(ClassListDTO.class);

        for (Map.Entry<String, List<MethodDTO>> entry : Flow.of(methodDTOs).groupBy(MethodDTO::getAccessLevel).entrySet()) {
            Node container  = new ElementNode(TagName.OL);

            container.append(createHeader(entry.getKey()));
            container.addAttribute("class", "list-group list-group-numbered mt-3");

            for (MethodDTO methodDTO : entry.getValue()) {
                container.append(createItem(methodDTO, builder));
            }

            wrapper.append(container);
        }

        return wrapper;
    }

    public Node createHeader(String text) {
        Node header     = new ElementNode(TagName.H5);
        Node headerText = new ElementNode(TagName.SPAN);

        headerText.addAttribute("class", "badge bg-info");
        headerText.append(new TextNode(text));
        header.append(headerText);

        return header;
    }

    public Node createItem(MethodDTO methodDTO, ClassSetBuilder builder) {
        Node item  = createListItemNode();
        Node inner = createInnerContainerNode();

        inner.append(createTextNode(createLinkNode(methodDTO)));
        inner.append(createTextNode(createParametersNode(methodDTO, builder)));
        inner.append(createTextNode(createReturnTypeNode(methodDTO, builder)));

        item.append(inner);

        return item;
    }

    public Node createListItemNode() {
        Node item = new ElementNode(TagName.LI);
        item.addAttribute("class", "list-group-item d-flex justify-content-between align-items-start");
        return item;
    }

    public Node createInnerContainerNode() {
        Node inner = new ElementNode(TagName.SPAN);
        inner.addAttribute("class", "ms-2 me-auto");
        return inner;
    }

    public Node createLinkNode(MethodDTO methodDTO) {
        Node link = new ElementNode(TagName.A);
        link.append(createMethodName(methodDTO));
        link.addAttribute("href", "/structured-console/_method/" + methodDTO.getName());
        return link;
    }

    public Node createTextNode(Node link) {
        Node text = new ElementNode(TagName.SPAN);
        text.addAttribute("class", "fw-bold");
        text.append(link);
        return text;
    }

    public Node createMethodName(MethodDTO methodDTO) {
        Node text = new ElementNode(TagName.SPAN);
        text.append(new TextNode(Reflections.getMethodName(methodDTO.getNativeMethod())));
        return text;
    }

    public Node createParametersNode(MethodDTO methodDTO, ClassSetBuilder builder) {
        Node         text            = new ElementNode(TagName.SPAN);
        ClassListDTO parametersTypes = methodDTO.getParametersTypes();
        int          parametersCount = parametersTypes.size();

        text.addAttribute("class", "fw-bold");
        text.append(new TextNode("("));

        if (parametersCount > 0) {
            int currentPosition = 0;
            for (ClassDTO parametersType : methodDTO.getParametersTypes()) {
                text.append(builder.createLinkNode(parametersType));
                if (parametersCount > ++currentPosition) {
                    text.append(new TextNode(", "));
                }
            }
        }

        text.append(new TextNode(")"));

        return text;
    }

    public Node createReturnTypeNode(MethodDTO methodDTO, ClassSetBuilder builder) {
        Node text = new ElementNode(TagName.SPAN);
        text.append(new TextNode(" : "));
        text.append(builder.createLinkNode(methodDTO.getReturnType()));
        return text;
    }


}
