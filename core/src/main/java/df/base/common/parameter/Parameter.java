package df.base.common.parameter;

import java.util.List;

public interface Parameter<V> {

    String getParameterName();

    V getParameterValue();

    List<V> getParameterValues();

}
