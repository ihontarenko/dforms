package df.base.common.mapping;

import df.base.PackageCoreRoot;
import df.base.common.libs.jbm.ClassUtils;
import df.base.common.reflection.ClassFinder;
import df.base.common.reflection.ReflectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

import static df.base.common.reflection.Reflections.findFirstConstructor;
import static df.base.common.reflection.Reflections.instantiate;

final public class MappingFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(MappingFactory.class);

    public static Mapping createDefault() {
        return new Mapping.DefaultMapping();
    }

    public static Mapping create() {
        return create(mapping -> {
            for (Class<?> mapperClass : ClassFinder.findImplementations(Mapper.class, PackageCoreRoot.class)) {
                try {
                    Object mapper = instantiate(findFirstConstructor(mapperClass));
                    mapping.register((Mapper<?, ?>) mapper);
                } catch (ReflectionException reflectionException) {
                    LOGGER.error("Failed to create mapper instance [{}]", reflectionException.getMessage());
                }
            }
        });
    }

    public static Mapping create(Consumer<Mapping> configurator) {
        Mapping mapping = createDefault();
        configurator.accept(mapping);
        return mapping;
    }

}
