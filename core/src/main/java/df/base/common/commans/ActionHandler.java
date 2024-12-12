package df.base.common.commans;

public interface ActionHandler<T> {
    T handle(CommandRequest request);
}
