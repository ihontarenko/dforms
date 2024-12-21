package df.base.common.mapping;

import df.base.common.libs.jbm.ClassUtils;
import df.base.common.reflection.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static df.base.common.libs.jbm.ClassUtils.getShortName;
import static df.base.common.reflection.Reflections.getInterfacesParameterizedType;

/**
 * Interface for handling mapping operations between different object types.
 * Provides methods to map objects, retrieve mappers, and register new mappers.
 */
public interface Mapping {

    Logger LOGGER = LoggerFactory.getLogger(Mapping.class);

    /**
     * Maps an object to a target type.
     *
     * @param source the source object to map
     * @param <R> the target type
     * @return the mapped object of type R
     */
    <R> R map(Object source);

    /**
     * Retrieves a mapper for the specified source object.
     *
     * @param source the source object
     * @param <S> the source type
     * @param <R> the target type
     * @return the appropriate Mapper instance
     * @throws MappingException if no suitable mapper is found
     */
    <S, R> Mapper<S, R> mapper(S source);

    /**
     * Registers a new mapper to handle specific object mappings.
     *
     * @param mapper the Mapper to register
     */
    void register(Mapper<?, ?> mapper);

    /**
     * Default implementation of the Mapping interface.
     * Maintains a registry of mappers and handles mapping operations.
     */
    class DefaultMapping implements Mapping {

        private final Map<Class<?>, Set<Mapper<Object, Object>>> mappers = new HashMap<>();

        /**
         * Retrieves a mapper for the specified source object.
         *
         * @param source the source object
         * @param <S> the source type
         * @param <R> the target type
         * @return the appropriate Mapper instance
         * @throws MappingException if no suitable mapper is found
         */
        @Override
        public <S, R> Mapper<S, R> mapper(S source) {
            Class<?> expectedType = resolveExpectedType(source);

            if (!mappers.containsKey(expectedType)) {
                throw new MappingException(
                        "No mapper candidates was found passed source '%s'".formatted(getShortName(source)));
            }

            for (Mapper<Object, Object> mapper : mappers.get(expectedType)) {
                if (mapper.supports(source)) {

                    LOGGER.info("Acceptable mapper '{}' for type '{}'.",
                                getShortName(mapper), getShortName(expectedType));

                    return (Mapper<S, R>) mapper;
                }
            }

            throw new MappingException(
                    "No applicable mapper was found for passed source '%s'".formatted(getShortName(source)));
        }

        /**
         * Maps an object to a target type by using the appropriate mapper.
         *
         * @param source the source object to map
         * @param <R> the target type
         * @return the mapped object of type R
         */
        @Override
        public <R> R map(Object source) {
            return this.<Object, R>mapper(source).map(source);
        }

        /**
         * Registers a new mapper by determining its preferred type.
         *
         * @param mapper the Mapper to register
         * @throws MappingException if the mapper type cannot be determined
         */
        @Override
        public void register(Mapper<?, ?> mapper) {
            Class<?> preferredType = resolvePreferredMappersType(mapper.getClass());

            if (preferredType == null) {
                throw new MappingException(
                        "Please generalize mapper '%s' to resolve acceptable type"
                                .formatted(getShortName(mapper)));
            }

            LOGGER.info("Register new mapper '{}' for preferred type '{}'.",
                        getShortName(mapper), getShortName(preferredType));

            mappers.computeIfAbsent(preferredType, type -> new HashSet<>())
                    .add((Mapper<Object, Object>) mapper);
        }

        /**
         * Resolves the preferred type for the specified mapper class.
         *
         * @param type the mapper class
         * @return the preferred type, or null if not found
         */
        private Class<?> resolvePreferredMappersType(Class<?> type) {
            Class<?> preferredType = null;

            for (Class<?> superClass : Reflections.getSuperClasses(type)) {
                if((preferredType = getInterfacesParameterizedType(superClass, Mapper.class, 0)) != null) {
                    break;
                }
            }

            return preferredType;
        }

        /**
         * Resolves the expected type of the source object for mapper lookup.
         *
         * @param source the source object
         * @return the resolved type
         */
        private Class<?> resolveExpectedType(Object source) {
            return mappers.containsKey(source.getClass()) ? source.getClass() : Object.class;
        }

    }
}
