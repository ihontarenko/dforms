package df.common.extensions.persistence.entity_graph.query;

import org.springframework.core.MethodParameter;
import org.springframework.data.jpa.repository.query.JpaParameters;
import org.springframework.data.repository.query.ParametersSource;
import org.springframework.data.util.TypeInformation;

import java.util.List;
import java.util.stream.Stream;

public class Parameters extends JpaParameters {

    private static final List<? extends Class<?>> SPECIAL_PARAMETERS = Stream.of(ParameterTypes.values())
            .map(ParameterTypes::type).toList();

    public Parameters(ParametersSource source) {
        super(source, parameter -> new Parameter(parameter, source.getDomainTypeInformation()));
    }

    private static class Parameter extends JpaParameters.JpaParameter {

        private final boolean isSpecialParameter;

        private Parameter(MethodParameter parameter, TypeInformation<?> domainType) {
            super(parameter, domainType);
            this.isSpecialParameter = SPECIAL_PARAMETERS.contains(parameter.getParameterType());
        }

        @Override
        public boolean isBindable() {
            return !isSpecialParameter && super.isBindable();
        }

        @Override
        public boolean isSpecialParameter() {
            return isSpecialParameter || super.isSpecialParameter();
        }

    }

}
