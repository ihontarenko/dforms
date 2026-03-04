package df.application.service.bc;

import df.application.html.bean_console.ClassBuilderRegistry;
import org.jmouse.common.dom.Node;
import org.jmouse.common.dom.NodeContext;
import org.jmouse.dom.constructor.NodeConstructor;
import org.jmouse.dom.constructor.NodeConstructorContext;
import org.jmouse.dom.constructor.NodeConstructorRegistry;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ClassManagementService {

    private final ClassService           classService;
    private final NodeConstructorContext builderContext;
    private final NodeContext            nodeContext;

    public ClassManagementService(ClassService classService, NodeContext nodeContext) {
        this.classService = classService;
        this.nodeContext = nodeContext;
        this.builderContext = new NodeConstructorContext();
        builderContext.setRegistry(new ClassBuilderRegistry());
    }

    public <T> String renderHtml(T objectDTO) {
        Class<T>                dtoDTO   = (Class<T>) Objects.requireNonNull(objectDTO).getClass();
        NodeConstructorRegistry strategy = builderContext.getRegistry();
        NodeConstructor<T>      builder  = strategy.getConstructor(dtoDTO);

        Node rootNode = builder.construct(objectDTO, builderContext);

        rootNode.execute(NodeContext.REORDER_NODE_CORRECTOR);
        rootNode.execute(NodeContext.NBSP_REPLACER_CORRECTOR);

        return rootNode.interpret(nodeContext);
    }


}
