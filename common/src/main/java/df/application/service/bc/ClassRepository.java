package df.application.service.bc;

import org.jmouse.core.matcher.Matcher;
import org.jmouse.core.reflection.ClassFinder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public record ClassRepository(Class<?>... baseClasses) {

    public ClassRepository(@Qualifier("baseClasses") Class<?>... baseClasses) {
        this.baseClasses = baseClasses;
    }

    public Collection<Class<?>> findClasses(Matcher<Class<?>> matcher) {
        return ClassFinder.findAll(matcher, ClassFinder.ORDER_CLASS_SIMPLE_NAME, baseClasses);
    }

}
