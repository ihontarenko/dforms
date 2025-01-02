package df.base.common.container;

import df.base.common.container.bean.context.AnnotationJbmContext;
import df.base.common.container.bean.context.JbmContext;
import df.base.common.container.bean.processor.JbmContextAwareBeanProcessor;
import df.base.common.container.bean.processor.LoggingBeanProcessor;
import df.base.common.container.bean.processor.PropertyValueBeanProcessor;
import df.base.common.container.example.User;
import df.base.common.container.example.services.ServiceA;
import df.base.common.container.example.services.ServiceInterface;
import df.base.common.container.example.services.Storage;
import df.base.common.container.bean.processor.InjectableFieldsFillerBeanProcessor;

public class Example {

    public static void main(String... arguments) {

        System.out.println(ServiceInterface.class.isAssignableFrom(ServiceA.class));
        System.out.println(ServiceA.class.isAssignableFrom(ServiceInterface.class));

        JbmContext context = AnnotationJbmContext.run(Example.class);
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
