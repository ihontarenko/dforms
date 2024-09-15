package df.base.common.pipeline;

import java.util.Map;

public interface PipelineContext {

    Map<Object, Object> getProperties();

    void setProperty(Object key, Object value);

    void setProperty(Object value);

    <R> R getProperty(Object key);

    <R> R getProperty(Object key, Object defaultValue);

    boolean hasProperty(Object key);

    boolean isStopped();

    void stopProcessing();

}
