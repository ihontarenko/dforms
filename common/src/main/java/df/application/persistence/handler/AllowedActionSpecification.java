package df.application.persistence.handler;

import df.common.specification.Specification;
import df.common.specification.SpecificationContext;
import df.application.persistence.entity.form.FieldConfig;

import java.util.List;

public class AllowedActionSpecification implements Specification<FieldConfig> {

    @Override
    public boolean isSatisfied(FieldConfig config, SpecificationContext context) {
        List<String> allowedActions = context.getAttribute("allowedActions");
        return allowedActions.contains(config.getConfigName());
    }

    @Override
    public RuntimeException getExceptionForViolation(FieldConfig config, SpecificationContext context) {
        return new NotAllowedActionException(
                "Action '%s' is disallowed for execution from '%s' context"
                        .formatted(config.getConfigName(), context.getAttribute("contextName")));
    }

    static class NotAllowedActionException extends RuntimeException {
        public NotAllowedActionException(String message) {
            super(message);
        }
    }

}
