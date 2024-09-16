package df.base.common.pipeline.context;

public interface PipelineArguments {

    <T> T getArgument(String name);

    void setArgument(Object name, Object parameter);

    void setArgument(Object parameter);

    boolean hasArgument(Object key);

}
