package df.base.common.pipeline;

import df.base.common.pipeline.context.PipelineArguments;
import df.base.common.pipeline.context.PipelineResult;

import java.util.Map;

public interface PipelineContext extends BeanProvider, BeanProviderAware {

    Map<Object, Object> getProperties();

    void setProperty(Object key, Object value);

    void setProperty(Object value);

    <R> R getProperty(Object key);

    <R> R getProperty(Object key, Object defaultValue);

    boolean hasProperty(Object key);

    PipelineResult getPipelineResult();

    void setPipelineResult(PipelineResult result);

    PipelineArguments getPipelineArguments();

    void setPipelineArguments(PipelineArguments arguments);

    boolean isStopped();

    void stopProcessing();

}
