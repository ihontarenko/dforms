package df.common.container;

import df.common.container.bean.context.AnnotationBeanContainerContext;
import df.common.container.bean.context.BeanContainerContext;
import df.common.container.bean.processor.BeanContainerContextAwareBeanProcessor;
import df.common.container.bean.processor.LoggingBeanProcessor;
import df.common.container.bean.processor.PropertyValueBeanProcessor;
import df.common.container.example.User;
import df.common.container.example.services.ServiceA;
import df.common.container.example.services.ServiceInterface;
import df.common.container.example.services.Storage;
import df.common.container.bean.processor.InjectableFieldsFillerBeanProcessor;

public class Example {

    public static void main(String... arguments) {

        System.out.println(ServiceInterface.class.isAssignableFrom(ServiceA.class));
        System.out.println(ServiceA.class.isAssignableFrom(ServiceInterface.class));

        BeanContainerContext context = AnnotationBeanContainerContext.run(Example.class);
        System.getProperties().put("project.name", "jdi jbm jac");

        context.addBeanProcessor(new LoggingBeanProcessor(System.out::println));
        context.addBeanProcessor(new BeanContainerContextAwareBeanProcessor());
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
