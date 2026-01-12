package df.common.pipeline.definition.model;

public record ParameterDefinition(
        String name,
        String value,
        String resolver,
        String converter
) { }