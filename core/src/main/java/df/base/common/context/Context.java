package df.base.common.context;

import java.util.Map;

public interface Context extends BeanProvider, BeanProviderAware {

    Map<Object, Object> getProperties();

    void setProperty(Object key, Object value);

    void setProperty(Object value);

    <R> R getProperty(Object key);

    <R> R getProperty(Object key, Object defaultValue);

    boolean hasProperty(Object key);

    ResultContext getResultContext();

    void setResultContext(ResultContext result);

    ArgumentsContext getArgumentsContext();

    void setArgumentsContext(ArgumentsContext arguments);

    boolean isStopped();

    void stopProcessing();

}
