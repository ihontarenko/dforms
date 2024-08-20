package df.base.common.jbm.bean.context;

public interface ApplicationContextAware {

    void setApplicationContext(JbmContext context);

    JbmContext getApplicationContext();

}
