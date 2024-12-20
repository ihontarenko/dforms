package df.base.common.mapping;

import df.base.common.libs.jbm.ClassUtils;
import df.base.common.reflection.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static df.base.common.reflection.Reflections.getInterfacesParameterizedType;

public interface Mapping {

    Logger LOGGER = LoggerFactory.getLogger(Mapping.class);

    <R> R map(Object source);

    <S, R> Mapper<S, R> mapper(S source);

    void register(Mapper<?, ?> mapper);

    class DefaultMapping implements Mapping {

        private final Map<Class<?>, Set<Mapper<Object, Object>>> mappers = new HashMap<>();

        @Override
        public <S, R> Mapper<S, R> mapper(S source) {
            return null;
        }

        @Override
        public <R> R map(Object source) {
            Class<?> expectedType = mappers.containsKey(source.getClass())
                    ? source.getClass() : Object.class;

            if (!mappers.containsKey(expectedType)) {
                throw new MappingException(
                        "No mapper candidates was found passed source '%s'"
                                .formatted(ClassUtils.getShortName(source)));
            }

            for (Mapper<Object, Object> mapper : mappers.get(expectedType)) {
                if (mapper.applicable(source)) {

                    LOGGER.info("Acceptable mapper '{}' for type '{}'.",
                                ClassUtils.getShortName(mapper), ClassUtils.getShortName(expectedType));

                    return (R) mapper.map(source);
                }
            }

            throw new MappingException(
                    "No applicable mapper was found for passed source '%s'"
                            .formatted(ClassUtils.getShortName(source)));
        }

        @Override
        public void register(Mapper<?, ?> mapper) {
            Optional<Class<?>> optionalClass = resolvePreferredMappersType(mapper.getClass());

            if (optionalClass.isEmpty()) {
                throw new MappingException(
                        "Please generalize mapper '%s' to resolve acceptable type"
                                .formatted(ClassUtils.getShortName(mapper)));
            }

            Class<?> preferredType = optionalClass.get();

            LOGGER.info("Register new mapper '{}' for preferred type '{}'.",
                        ClassUtils.getShortName(mapper), ClassUtils.getShortName(preferredType));

            mappers.computeIfAbsent(preferredType, type -> new HashSet<>())
                    .add((Mapper<Object, Object>) mapper);
        }

        private Optional<Class<?>> resolvePreferredMappersType(Class<?> type) {
            Optional<Class<?>> preferredType = Optional.empty();

            for (Class<?> superClass : Reflections.getSuperClasses(type)) {
                preferredType = getInterfacesParameterizedType(superClass, Mapper.class, 0);

                if (preferredType.isPresent()) {
                    break;
                }
            }

            return preferredType;
        }

    }

}
