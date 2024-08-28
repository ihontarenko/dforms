package df.base.internal.jbm.bean.context;

public interface JbmContextAware {

    void setApplicationContext(JbmContext context);

    JbmContext getApplicationContext();

}
