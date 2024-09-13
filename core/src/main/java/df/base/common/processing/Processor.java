package df.base.common.processing;

public interface Processor<C extends ProcessingContext, T> {

    void process(T target, C context);

    boolean supports(T target, C context);

}
