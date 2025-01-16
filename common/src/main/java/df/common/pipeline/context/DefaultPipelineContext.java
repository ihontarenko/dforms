package df.common.pipeline.context;

import svit.context.*;

public class DefaultPipelineContext extends AbstractContext implements
        PipelineContext, ArgumentsContext, ResultContext {

    private ResultContext    result;
    private ArgumentsContext arguments;

    public DefaultPipelineContext() {
        this(null);
    }

    public DefaultPipelineContext(BeanProvider beanProvider) {
        super(beanProvider);
        this.result = new DefaultPipelineResult();
        this.arguments = new DefaultPipelineArguments();
    }

    @Override
    public ResultContext getResultContext() {
        return result;
    }

    @Override
    public void setResultContext(ResultContext result) {
        this.result = result;
    }

    @Override
    public ArgumentsContext getArgumentsContext() {
        return arguments;
    }

    @Override
    public void setArgumentsContext(ArgumentsContext arguments) {
        this.arguments = arguments;
    }

    @Override
    public <T> T requireArgument(Object name) {
        return getArgumentsContext().requireArgument(name);
    }

    @Override
    public <T> T getArgument(Object name) {
        return getArgumentsContext().getArgument(name);
    }

    @Override
    public void setArgument(Object name, Object argument) {
        getArgumentsContext().setArgument(name, argument);
    }

    @Override
    public void setArgument(Object argument) {
        getArgumentsContext().setArgument(argument);
    }

    @Override
    public void setArguments(Object... arguments) {
        getArgumentsContext().setArguments(arguments);
    }

    @Override
    public boolean hasArgument(Object name) {
        return getArgumentsContext().hasArgument(name);
    }

    @Override
    public <T> T getReturnValue() {
        return getResultContext().getReturnValue();
    }

    @Override
    public void setReturnValue(Object value) {
        getResultContext().setReturnValue(value);
    }

    @Override
    public boolean hasErrors() {
        return getResultContext().hasErrors();
    }

    @Override
    public Iterable<ErrorDetails> getErrors() {
        return getResultContext().getErrors();
    }

    @Override
    public ErrorDetails getError(String name) {
        return getResultContext().getError(name);
    }

    @Override
    public void addError(ErrorDetails errorDetails) {
        getResultContext().addError(errorDetails);
    }

    @Override
    public void addError(String code, String message) {
        getResultContext().addError(code, message);
    }

    @Override
    public void cleanup() {
        getResultContext().cleanup();
    }

    public static class DefaultPipelineResult extends AbstractResultContext {

    }

    public static class DefaultPipelineArguments extends AbstractArgumentsContext {

    }

}
