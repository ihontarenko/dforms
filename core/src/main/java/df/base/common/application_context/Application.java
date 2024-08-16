package df.base.common.application_context;

import df.base.common.application_context.bean.context.AnnotationApplicationContext;
import df.base.common.application_context.bean.context.ApplicationContext;
import df.base.common.application_context.bean.processor.ApplicationContextAwareBeanProcessor;
import df.base.common.application_context.bean.processor.InjectableFieldsFillerBeanProcessor;
import df.base.common.application_context.bean.processor.LoggingBeanProcessor;
import df.base.common.application_context.bean.processor.PropertyValueBeanProcessor;
import df.base.common.application_context.sandbox.User;
import df.base.common.application_context.sandbox.services.Storage;

public class Application {

    public static void main(String... arguments) {
        ApplicationContext context = AnnotationApplicationContext.run(Application.class);

        context.addBeanProcessor(new LoggingBeanProcessor(System.out::println));
        context.addBeanProcessor(new ApplicationContextAwareBeanProcessor());
        context.addBeanProcessor(new InjectableFieldsFillerBeanProcessor());
        context.addBeanProcessor(new PropertyValueBeanProcessor(
                System.getProperties(), System.getenv()
        ));

        Storage.InMemoryStorage storage = (Storage.InMemoryStorage) context.getBean(Storage.class);

        System.out.println(storage.getJavaHome());
        System.out.println(storage);
        System.out.println("service: " + storage.getService());

        System.out.println(context.getBean(User.class));

        System.out.println("FINISH");
    }

}
