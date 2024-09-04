package df.base.common.libs.jbm.bean.definition;

public class DuplicateBeanDefinitionException extends RuntimeException {

    public DuplicateBeanDefinitionException(BeanDefinition definition) {
        super("BEAN DEFINITION : '" + definition.getBeanName() + "' ALREADY PRESENT IN FACTORY");
    }

}
