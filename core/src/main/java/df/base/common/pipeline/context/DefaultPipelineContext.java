package df.base.common.pipeline.context;

import df.base.common.pipeline.PipelineContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class DefaultPipelineContext implements PipelineContext {

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
    public boolean hasProperty(Object key) {
        return properties.containsKey(key);
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

    }

    public static class DefaultPipelineArguments implements PipelineArguments {

        private final Map<Object, Object> arguments = new HashMap<>();

        @Override
        public <T> T getArgument(String name) {
            return (T) arguments.getOrDefault(name, new Object());
        }

        @Override
        public void setArgument(Object name, Object parameter) {
            arguments.put(name, parameter);
        }

        @Override
        public void setArgument(Object parameter) {
            setArgument(requireNonNull(parameter).getClass(), parameter);
        }

        @Override
        public boolean hasArgument(Object name) {
            return arguments.containsKey(name);
        }

    }

}
