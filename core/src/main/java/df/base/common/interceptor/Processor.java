package df.base.common.interceptor;

public interface Processor<C extends ProcessingContext, T> {

    void process(T target, C context);

    boolean supports(T target, C context);

}
