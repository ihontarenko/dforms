package df.base.common.pipeline.context;

public interface PipelineArguments {

    <T> T requireArgument(Object name);

    <T> T getArgument(Object name);

    void setArgument(Object name, Object argument);

    void setArgument(Object argument);

    void setArguments(Object... arguments);

    boolean hasArgument(Object name);

}
