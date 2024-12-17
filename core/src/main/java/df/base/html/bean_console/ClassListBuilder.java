package df.base.html.bean_console;

import df.base.common.elements.Node;
import df.base.common.elements.TagName;
import df.base.common.elements.builder.NodeBuilder;
import df.base.common.elements.builder.NodeBuilderContext;
import df.base.common.elements.node.ElementNode;
import df.base.common.elements.node.TextNode;
import df.base.common.flow.Flow;
import df.base.dto.reflection.ClassDTO;
import df.base.dto.reflection.ClassSetDTO;

import java.util.List;
import java.util.Map;

public class ClassListBuilder implements NodeBuilder<ClassSetDTO> {

    @Override
    public Node build(ClassSetDTO classDTOs, NodeBuilderContext ctx) {
        Node                        information    = new ElementNode(TagName.H4);
        Node                        wrapper        = new ElementNode(TagName.DIV);
        Map<String, List<ClassDTO>> groupedClasses = Flow.of(classDTOs).groupBy(dto -> dto.getPackage().getName());

        for (var entry : groupedClasses.entrySet()) {
            Node container = new ElementNode(TagName.DIV);
            Node title     = new ElementNode(TagName.H5);

            title.append(new TextNode(entry.getKey()));
            wrapper.append(new ElementNode(TagName.HR));
            wrapper.append(title);
            container.addAttribute("class", "list-group mt-3");

            for (ClassDTO classDTO : entry.getValue()) {
                container.append(createLink(classDTO));
            }

            wrapper.append(container);
        }

        createInformationHeader(information, classDTOs);

        wrapper.prepend(information);

        return wrapper;
    }

    private Node createLink(ClassDTO classDTO) {
        Node link = new ElementNode(TagName.A);

        link.append(new TextNode(classDTO.getShortName()));

        link.addAttribute("class", "list-group-item list-group-item-action class-item-link");
        link.addAttribute("href", "/bean-console/_class/" + classDTO.getFullName());

        return link;
    }

    private void createInformationHeader(Node information, ClassSetDTO classDTOs) {
        information.append(new TextNode("Classes Count: %d".formatted(classDTOs.size())));
    }

}
