package df.base.internal;

public enum ValueHolder {

    HOLDER(new ThreadLocal<>());

    private final ThreadLocal<Object> local;

    ValueHolder(ThreadLocal<Object> local) {
        this.local = local;
    }

    public <T> void set(T value) {
        local.set(value);
    }

    @SuppressWarnings({"unchecked"})
    public <T> T get() {
        return (T) local.get();
    }

    public boolean has() {
        return local.get() != null;
    }

}
