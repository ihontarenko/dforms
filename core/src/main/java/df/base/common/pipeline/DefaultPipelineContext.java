package df.base.common.pipeline;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DefaultPipelineContext implements PipelineContext {

    private final Map<Object, Object> properties = new HashMap<>();
    private boolean                   stopped    = false;

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
    public boolean isStopped() {
        return stopped;
    }

    @Override
    public void stopProcessing() {
        this.stopped = true;
    }

}
