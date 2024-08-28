package df.base.internal.jbm;

import df.base.internal.jbm.bean.context.AnnotationJbmContext;
import df.base.internal.jbm.bean.context.JbmContext;
import df.base.internal.jbm.bean.processor.JbmContextAwareBeanProcessor;
import df.base.internal.jbm.bean.processor.InjectableFieldsFillerBeanProcessor;
import df.base.internal.jbm.bean.processor.LoggingBeanProcessor;
import df.base.internal.jbm.bean.processor.PropertyValueBeanProcessor;
import df.base.internal.jbm.example.User;
import df.base.internal.jbm.example.services.ServiceInterface;
import df.base.internal.jbm.example.services.Storage;

public class JbmExample {

    public static void main(String... arguments) {
        JbmContext context = AnnotationJbmContext.run(JbmExample.class);

        System.getProperties().put("project.name", "jdi jbm jac");

        context.addBeanProcessor(new LoggingBeanProcessor(System.out::println));
        context.addBeanProcessor(new JbmContextAwareBeanProcessor());
        context.addBeanProcessor(new InjectableFieldsFillerBeanProcessor());
        context.addBeanProcessor(new PropertyValueBeanProcessor(
                System.getProperties(), System.getenv()
        ));

        Storage.InMemoryStorage storage = (Storage.InMemoryStorage) context.getBean(Storage.class);

        System.out.println(storage.getJavaHome());
        System.out.println(storage);

        System.out.println(context.getBean(User.class));
        System.out.println(context.getBean(ServiceInterface.class, "SERVICE_D"));

        System.out.println("FINISH");
    }

}
