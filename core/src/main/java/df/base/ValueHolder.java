package df.base;

public enum ValueHolder {
    HOLDER(new ThreadLocal<>());

    private final ThreadLocal<String> local;

    ValueHolder(ThreadLocal<String> local) {
        this.local = local;
    }

    public void set(String value) {
        local.set(value);
    }

    public String get() {
        return local.get();
    }

    public boolean has() {
        return local.get() != null;
    }

}
