package df.base.common.application_context.bean.context;

public interface ApplicationContextAware {

    void setApplicationContext(ApplicationContext context);

    ApplicationContext getApplicationContext();

}
