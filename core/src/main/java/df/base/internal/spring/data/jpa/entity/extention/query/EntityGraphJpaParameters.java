package df.base.internal.spring.data.jpa.entity.extention.query;

import df.base.internal.spring.data.jpa.entity.extention.EntityGraph;
import org.springframework.core.MethodParameter;
import org.springframework.data.jpa.repository.query.JpaParameters;
import org.springframework.data.repository.query.ParametersSource;
import org.springframework.data.util.TypeInformation;

public class EntityGraphJpaParameters extends JpaParameters {

    public EntityGraphJpaParameters(ParametersSource source) {
        super(source, parameter -> new Parameter(parameter, source.getDomainTypeInformation()));
    }

    private static class Parameter extends JpaParameters.JpaParameter {

        private final boolean entityGraph;

        private Parameter(MethodParameter parameter, TypeInformation<?> domainType) {
            super(parameter, domainType);
            this.entityGraph = EntityGraph.class.isAssignableFrom(parameter.getParameterType());
        }

        @Override
        public boolean isBindable() {
            return !entityGraph && super.isBindable();
        }

        @Override
        public boolean isSpecialParameter() {
            return entityGraph || super.isSpecialParameter();
        }
    }

}
