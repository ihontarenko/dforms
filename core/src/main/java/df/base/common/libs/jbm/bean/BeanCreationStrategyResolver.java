package df.base.common.libs.jbm.bean;

import df.base.common.libs.jbm.bean.creation.BeanCreationStrategy;
import df.base.common.libs.jbm.bean.definition.BeanDefinition;

import java.util.Arrays;
import java.util.List;

public class BeanCreationStrategyResolver {

    private final List<BeanCreationStrategy> strategies;

    public BeanCreationStrategyResolver(BeanCreationStrategy... strategies) {
        this.strategies = Arrays.asList(strategies);
    }

    public BeanCreationStrategy resolve(BeanDefinition definition) {
        for (BeanCreationStrategy strategy : strategies) {
            if (strategy.canCreate(definition)) {
                definition.setBeanCreationStrategy(strategy);
                return strategy;
            }
        }
        throw new IllegalArgumentException("UNSUPPORTED BEAN DEFINITION: \n" + definition);
    }
}
