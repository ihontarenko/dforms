package df.base.common.libs.jbm.bean.processor;

public class BeanProcessorException extends RuntimeException {

    public BeanProcessorException(String message) {
        super(message);
    }

    public BeanProcessorException(String message, Throwable cause) {
        super(message, cause);
    }
}
