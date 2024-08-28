package df.base.internal.jbm.bean;

public class NamedDependency implements BeanDependency {

    private final Class<?> type;
    private final String name;

    public NamedDependency(Class<?> type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public Class<?> getBeanClass() {
        return type;
    }

    @Override
    public String getBeanName() {
        return name;
    }

}
