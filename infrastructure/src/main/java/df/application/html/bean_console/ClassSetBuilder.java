package df.application.html.bean_console;

import org.jmouse.common.dom.Node;
import org.jmouse.common.dom.TagName;
import org.jmouse.common.dom.builder.NodeBuilder;
import org.jmouse.common.dom.builder.NodeBuilderContext;
import org.jmouse.common.dom.node.ElementNode;
import org.jmouse.common.dom.node.TextNode;
import df.application.dto.reflection.ClassDTO;
import df.application.dto.reflection.ClassListDTO;

/**
 * A builder class for generating an HTML representation of a set of Java classes.
 * <p>
 * The {@code ClassListBuilder} organizes a collection of {@link ClassDTO} instances grouped by
 * their package names and constructs an HTML structure to display this information.
 * The output includes a header indicating the total number of classes and a list
 * of classes grouped by their package names.
 * </p>
 *
 * <p>
 * This class implements the {@link NodeBuilder} interface and operates on
 * {@link ClassListDTO} data structures.
 * </p>
 *
 * <p><b>Example Usage:</b></p>
 * <pre>{@code
 * ClassListBuilder builder = new ClassListBuilder();
 * Node result = builder.build(classSetDTO, context);
 * }</pre>
 */
public class ClassSetBuilder implements NodeBuilder<ClassListDTO> {

    /**
     * Builds an HTML representation of the provided set of classes.
     *
     * @param classDTOs the set of class definitions to be processed.
     * @param ctx the context for building the nodes.
     * @return a {@link Node} representing the HTML structure of the class set.
     */
    @Override
    public Node build(ClassListDTO classDTOs, NodeBuilderContext ctx) {
        Node wrapper   = new ElementNode(TagName.DIV);
        Node container = new ElementNode(TagName.OL);

        container.addAttribute("class", "list-group list-group-numbered mt-3");

        for (ClassDTO classDTO : classDTOs) {
            container.append(createItem(classDTO));
        }

        wrapper.append(container);

        wrapper.prepend(createHeader(classDTOs));
        wrapper.addAttribute("class", "mt-3");

        return wrapper;
    }

    /**
     * Creates an HTML representation of a single class.
     *
     * @param classDTO the class definition to be represented.
     * @return a {@link Node} representing the HTML structure of the class item.
     */
    public Node createItem(ClassDTO classDTO) {
        Node item  = createListItemNode();
        Node wrapper = createInnerContainerNode();

        wrapper.append(createTextNode(createLinkNode(classDTO)));
        wrapper.append(createSmallNode(classDTO));
        item.append(wrapper);

        return item;
    }

    /**
     * Creates the base list item node.
     *
     * @return a {@link Node} representing the list item.
     */
    public Node createListItemNode() {
        Node item = new ElementNode(TagName.LI);
        item.addAttribute("class", "list-group-item d-flex justify-content-between align-items-start");
        return item;
    }

    /**
     * Creates the inner container node.
     *
     * @return a {@link Node} representing the inner container.
     */
    public Node createInnerContainerNode() {
        Node inner = new ElementNode(TagName.DIV);
        inner.addAttribute("class", "ms-2 me-auto");
        return inner;
    }

    /**
     * Creates the link node pointing to the class details.
     *
     * @param classDTO the class definition to link to.
     * @return a {@link Node} representing the link.
     */
    public Node createLinkNode(ClassDTO classDTO) {
        Node    item     = new ElementNode(TagName.SPAN);
        boolean linkless = classDTO.isPrimitive() || classDTO.isForeign();

        if (!linkless) {
            item = new ElementNode(TagName.A);
            item.addAttribute("href", "/structured-console/_class/" + classDTO.getFullName());
            item.addAttribute("class", "text-primary");
        }

        item.append(new TextNode(classDTO.getName()));

        if (classDTO.isArray()) {
            item.append(new TextNode("[]"));
        }

        return item;
    }

    /**
     * Creates a bold text node containing the link.
     *
     * @param link the link node to include in the text.
     * @return a {@link Node} representing the bold text.
     */
    public Node createTextNode(Node link) {
        Node text = new ElementNode(TagName.DIV);
        text.addAttribute("class", "fw-bold");
        text.append(link);
        return text;
    }

    /**
     * Creates the small node containing the package name.
     *
     * @param classDTO the class definition to extract the package name from.
     * @return a {@link Node} representing the package name.
     */
    public Node createSmallNode(ClassDTO classDTO) {
        Node small = new ElementNode(TagName.SMALL);
        small.append(new TextNode(classDTO.getFullName()));
        return small;
    }


    public Node createHeader(ClassListDTO classDTOs) {
        Node header = new ElementNode(TagName.H4);
        header.append(new TextNode("Classes Count: %d".formatted(classDTOs.size())));
        return header;
    }

}
