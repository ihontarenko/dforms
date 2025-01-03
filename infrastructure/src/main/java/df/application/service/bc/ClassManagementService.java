package df.application.service.bc;

import df.application.html.bean_console.ClassBuilderRegistry;
import df.common.elements.Node;
import df.common.elements.NodeContext;
import df.common.elements.builder.NodeBuilder;
import df.common.elements.builder.NodeBuilderContext;
import df.common.elements.builder.NodeBuilderRegistry;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ClassManagementService {

    private final ClassService       classService;
    private final NodeBuilderContext builderContext;
    private final NodeContext        nodeContext;

    public ClassManagementService(ClassService classService, NodeContext nodeContext) {
        this.classService = classService;
        this.nodeContext = nodeContext;
        this.builderContext = new NodeBuilderContext();
        builderContext.setRegistry(new ClassBuilderRegistry());
    }

    public <T> String renderHtml(T objectDTO) {
        Class<T>            dtoDTO   = (Class<T>) Objects.requireNonNull(objectDTO).getClass();
        NodeBuilderRegistry strategy = builderContext.getRegistry();
        NodeBuilder<T>      builder  = strategy.getBuilder(dtoDTO);

        Node rootNode = builder.build(objectDTO, builderContext);

        rootNode.execute(NodeContext.REORDER_NODE_CORRECTOR);
        rootNode.execute(NodeContext.NBSP_REPLACER_CORRECTOR);

        return rootNode.interpret(nodeContext);
    }


}
