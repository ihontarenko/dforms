package df.base.common.libs.jbm.bean.context;

public interface JbmContextAware {

    void setApplicationContext(JbmContext context);

    JbmContext getApplicationContext();

}
