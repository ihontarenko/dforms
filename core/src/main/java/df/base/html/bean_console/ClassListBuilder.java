package df.base.html.bean_console;

import df.base.common.elements.Node;
import df.base.common.elements.TagName;
import df.base.common.elements.builder.NodeBuilder;
import df.base.common.elements.builder.NodeBuilderContext;
import df.base.common.elements.node.ElementNode;
import df.base.common.elements.node.TextNode;
import df.base.dto.reflection.ClassDTO;

import java.util.Set;

public class ClassListBuilder implements NodeBuilder<Set<ClassDTO>> {

    @Override
    public Node build(Set<ClassDTO> classDTOs, NodeBuilderContext ctx) {
        Node ul = new ElementNode(TagName.UL);

        for (ClassDTO classDTO : classDTOs) {
            Node li = new ElementNode(TagName.LI);

            li.append(new TextNode(classDTO.getFullName()));

            ul.append(li);
        }

        return ul;
    }

}
