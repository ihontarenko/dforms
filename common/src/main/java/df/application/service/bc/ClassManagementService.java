package df.application.service.bc;

import df.application.html.bean_console.ClassBuilderRegistry;
import org.jmouse.dom.CorrectNodeDepth;
import org.jmouse.dom.NbspReplacerCorrector;
import org.jmouse.dom.Node;
import org.jmouse.dom.constructor.NodeConstructor;
import org.jmouse.dom.constructor.NodeConstructorContext;
import org.jmouse.dom.constructor.NodeConstructorRegistry;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ClassManagementService {

    private final ClassService           classService;
    private final NodeConstructorContext builderContext;

    public ClassManagementService(ClassService classService) {
        this.classService = classService;
        this.builderContext = new NodeConstructorContext();
        builderContext.setRegistry(new ClassBuilderRegistry());
    }

    public <T> String renderHtml(T objectDTO) {
        Class<T>                dtoDTO   = (Class<T>) Objects.requireNonNull(objectDTO).getClass();
        NodeConstructorRegistry strategy = builderContext.getRegistry();
        NodeConstructor<T>      builder  = strategy.getConstructor(dtoDTO);

        Node rootNode = builder.construct(objectDTO, builderContext);

        rootNode.execute(new CorrectNodeDepth());
        rootNode.execute(new NbspReplacerCorrector());

//        return rootNode.interpret(nodeContext);
        return null;
    }


}
