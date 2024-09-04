package df.base.common.jbm.bean.context;

public interface JbmContextAware {

    void setApplicationContext(JbmContext context);

    JbmContext getApplicationContext();

}
