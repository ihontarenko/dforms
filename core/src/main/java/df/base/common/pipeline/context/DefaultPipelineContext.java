package df.base.common.pipeline.context;

import df.base.common.pipeline.PipelineContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class DefaultPipelineContext implements PipelineContext, PipelineArguments, PipelineResult {

    private final Map<Object, Object> properties = new HashMap<>();
    private       boolean             stopped    = false;
    private       PipelineResult      result;
    private       PipelineArguments   parameters;

    public DefaultPipelineContext() {
        this.result = new DefaultPipelineResult();
        this.parameters = new DefaultPipelineArguments();
    }

    @Override
    public Map<Object, Object> getProperties() {
        return properties;
    }

    @Override
    public void setProperty(Object key, Object value) {
        properties.put(key, value);
    }

    @Override
    public void setProperty(Object value) {
        setProperty(Objects.requireNonNullElse(value, new Object()).getClass(), value);
    }

    @Override
    public <R> R getProperty(Object key) {
        return getProperty(key, null);
    }

    @Override
    public <R> R getProperty(Object key, Object defaultValue) {
        return (R) properties.getOrDefault(key, defaultValue);
    }

    @Override
    public boolean hasProperty(Object key) {
        return properties.containsKey(key);
    }

    @Override
    public PipelineResult getPipelineResult() {
        return result;
    }

    @Override
    public void setPipelineResult(PipelineResult result) {
        this.result = result;
    }

    @Override
    public PipelineArguments getPipelineArguments() {
        return parameters;
    }

    @Override
    public void setPipelineArguments(PipelineArguments arguments) {
        this.parameters = arguments;
    }

    @Override
    public <T> T requireArgument(Object name) {
        return getPipelineArguments().requireArgument(name);
    }

    @Override
    public <T> T getArgument(Object name) {
        return getPipelineArguments().getArgument(name);
    }

    @Override
    public void setArgument(Object name, Object argument) {
        getPipelineArguments().setArgument(name, argument);
    }

    @Override
    public void setArgument(Object argument) {
        getPipelineArguments().setArgument(argument);
    }

    @Override
    public void setArguments(Object... arguments) {
        getPipelineArguments().setArguments(arguments);
    }

    @Override
    public boolean hasArgument(Object name) {
        return getPipelineArguments().hasArgument(name);
    }

    @Override
    public <T> T getReturnValue() {
        return getPipelineResult().getReturnValue();
    }

    @Override
    public void setReturnValue(Object value) {
        getPipelineResult().setReturnValue(value);
    }

    @Override
    public boolean hasErrors() {
        return getPipelineResult().hasErrors();
    }

    @Override
    public Iterable<ErrorDetails> getErrors() {
        return getPipelineResult().getErrors();
    }

    @Override
    public ErrorDetails getError(String name) {
        return getPipelineResult().getError(name);
    }

    @Override
    public void addError(ErrorDetails errorDetails) {
        getPipelineResult().addError(errorDetails);
    }

    @Override
    public void addError(String code, String message) {
        getPipelineResult().addError(code, message);
    }

    @Override
    public boolean isStopped() {
        return stopped;
    }

    @Override
    public void stopProcessing() {
        this.stopped = true;
    }

    public static class DefaultPipelineResult implements PipelineResult {

        private final Map<String, ErrorDetails> errors;
        private       Object                    value;

        public DefaultPipelineResult() {
            this.errors = new HashMap<>();
        }

        @Override
        public <T> T getReturnValue() {
            return (T) value;
        }

        @Override
        public void setReturnValue(Object value) {
            this.value = value;
        }

        @Override
        public boolean hasErrors() {
            return errors.size() > 0;
        }

        @Override
        public Iterable<ErrorDetails> getErrors() {
            return errors.values();
        }

        @Override
        public ErrorDetails getError(String name) {
            return errors.get(name);
        }

        @Override
        public void addError(ErrorDetails errorDetails) {
            errors.put(errorDetails.code(), errorDetails);
        }

        @Override
        public void addError(String code, String message) {
            addError(new ErrorDetails(code, message));
        }
    }

    public static class DefaultPipelineArguments implements PipelineArguments {

        private final Map<Object, Object> arguments = new HashMap<>();

        @Override
        public <T> T requireArgument(Object name) {
            if (hasArgument(name)) {
                return getArgument(name);
            }

            throw new ArgumentNotFoundException("Required argument not found [%s]".formatted(name));
        }

        @Override
        public <T> T getArgument(Object name) {
            return (T) arguments.getOrDefault(name, new Object());
        }

        @Override
        public void setArgument(Object name, Object argument) {
            arguments.put(name, argument);
        }

        @Override
        public void setArgument(Object argument) {
            setArgument(requireNonNull(argument).getClass(), argument);
        }

        @Override
        public void setArguments(Object... arguments) {
            for (Object argument : arguments) {
                setArgument(argument);
            }
        }

        @Override
        public boolean hasArgument(Object name) {
            return arguments.containsKey(name);
        }

        @Override
        public String toString() {
            return "ARGUMENTS: %s".formatted(arguments);
        }
    }

}
