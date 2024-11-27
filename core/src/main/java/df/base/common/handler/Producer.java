package df.base.common.handler;

public interface Producer<R, T, C> {
    R produce(T target, C ctx);
}
