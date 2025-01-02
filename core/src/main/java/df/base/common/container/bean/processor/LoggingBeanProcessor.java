package df.base.common.container.bean.processor;

import df.base.common.container.bean.context.BeanContainerContext;

import java.util.function.Consumer;

public class LoggingBeanProcessor implements BeanProcessor {

    private final Consumer<String> logger;

    public LoggingBeanProcessor(Consumer<String> logger) {
        this.logger = logger;
    }

    @Override
    public void process(Object bean, BeanContainerContext context) {
        logger.accept(
                "-- BEAN CREATED: [%s@%x] [%s]".formatted(bean.getClass().getName(), bean.hashCode(), bean)
        );
    }

}
