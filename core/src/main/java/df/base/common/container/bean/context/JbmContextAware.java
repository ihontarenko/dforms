package df.base.common.container.bean.context;

public interface JbmContextAware {

    void setApplicationContext(JbmContext context);

    JbmContext getApplicationContext();

}
