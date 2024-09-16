package df.base.common.pipeline;

public interface ParameterValueResolver {
    Object resolve(String name, String value, PipelineContext ctx);
}
