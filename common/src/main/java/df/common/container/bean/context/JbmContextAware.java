package df.common.container.bean.context;

public interface JbmContextAware {

    void setApplicationContext(BeanContainerContext context);

    BeanContainerContext getApplicationContext();

}
