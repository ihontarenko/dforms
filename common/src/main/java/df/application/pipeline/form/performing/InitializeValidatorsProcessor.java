package df.application.pipeline.form.performing;

import df.application.dto.form.FieldConfigDTO;
import org.jetbrains.annotations.NotNull;
import org.jmouse.core.mapping.Mapper;
import org.jmouse.core.mapping.Mappers;
import org.jmouse.core.mapping.config.MappingConfig;
import org.jmouse.el.ExpressionLanguage;
import org.jmouse.pipeline.PipelineResult;
import org.jmouse.pipeline.PipelineProcessor;
import org.jmouse.pipeline.context.PipelineContext;
import org.jmouse.core.context.mutable.MutableArgumentsContext;
import org.jmouse.validator.*;
import org.jmouse.validator.constraint.adapter.core.ConstraintSchemaValidator;
import org.jmouse.validator.constraint.adapter.el.ConstraintELModule;
import org.jmouse.validator.constraint.adapter.el.ConstraintExpressionSupport;
import org.jmouse.validator.constraint.api.Constraint;
import org.jmouse.validator.constraint.constraint.*;
import org.jmouse.validator.constraint.dsl.ConstraintSchemas;
import org.jmouse.validator.constraint.handler.ConstraintHandler;
import org.jmouse.validator.constraint.handler.SchemaSelector;
import org.jmouse.validator.constraint.processor.ConstraintProcessor;
import org.jmouse.validator.constraint.registry.ConstraintTypeRegistry;
import org.jmouse.validator.constraint.registry.InMemoryConstraintSchemaRegistry;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InitializeValidatorsProcessor implements PipelineProcessor {

    public enum SchemaHint { EXPRESSION }

    @Override
    public PipelineResult process(
            PipelineContext context, MutableArgumentsContext arguments, PipelineResult previous
    ) throws Exception {
        Map<String, List<FieldConfigDTO>> validationConfigs = arguments.getRequiredArgument("VALIDATION_CONFIGS");

        InMemoryConstraintSchemaRegistry schemaRegistry    = new InMemoryConstraintSchemaRegistry();
        ConstraintProcessor              processor         = new ConstraintProcessor();
        ConstraintHandler                constraintHandler = new ConstraintHandler(processor);

        ConstraintExpressionSupport support = getConstraintExpressionSupport();

        ConstraintSchemas.Builder builder = ConstraintSchemas.builder("DF.expression");

        validationConfigs.forEach((fieldName, configs) -> {
            for (FieldConfigDTO configDTO : configs) {
                Constraint constraint = support.parse(configDTO.getValue());
                builder.field(fieldName).use(constraint).add().done();
            }
        });

        schemaRegistry.register(builder.build());

        ValidatorRegistry registry = getDefaultValidatorRegistry(schemaRegistry, constraintHandler);

        ValidationProcessor validationProcessor = ValidationProcessors.builder()
                .validatorRegistry(registry)
                .errorsFactory(new JavaBeanErrorsFactory())
                .validationPolicy(ValidationPolicy.COLLECT_ALL)
                .build();

        context.setValue(ValidationProcessor.class, validationProcessor);

        return PipelineResult.of(ReturnCodes.VALIDATE);
    }

    private static @NotNull ValidatorRegistry getDefaultValidatorRegistry(
            InMemoryConstraintSchemaRegistry schemaRegistry, ConstraintHandler constraintHandler
    ) {
        SchemaSelector                                    selector      = (objectName, hints) -> "DF.expression";
        ConstraintSchemaValidator.ValidationHintsSupplier hintsSupplier = () -> Hints.of(SchemaHint.EXPRESSION);
        ValidatorRegistry                                 registry      = new DefaultValidatorRegistry();

        Validator constraintValidator = new ConstraintSchemaValidator(
                schemaRegistry,
                constraintHandler,
                selector,
                hintsSupplier
        );

        registry.register(constraintValidator);

        return registry;
    }

    public static ConstraintExpressionSupport getConstraintExpressionSupport() {
        ConstraintTypeRegistry typeRegistry = new ConstraintTypeRegistry()
                .register("minmax", MinMaxConstraint.class)
                .register("required", RequiredConstraint.class)
                .register("notBlank", NotBlankConstraint.class)
                .register("oneOf", OneOfConstraint.class)
                .register("webLink", WebLinkConstraint.class);

        return ConstraintELModule.create(new ExpressionLanguage(), typeRegistry, getMapper());
    }

    public static Mapper getMapper() {
        return Mappers.builder()
                .config(MappingConfig.builder()
                        .listFactory(LinkedList::new)
                        .build())
                .build();
    }

}
