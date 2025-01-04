package df.application.service.bc;

import df.common.matcher.Matcher;
import df.common.reflection.ClassFinder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public record ClassRepository(Class<?>... baseClasses) {

    public ClassRepository(@Qualifier("baseClasses") Class<?>... baseClasses) {
        this.baseClasses = baseClasses;
    }

    public Set<Class<?>> findClasses(Matcher<Class<?>> matcher) {
        return ClassFinder.findAll(matcher, baseClasses);
    }

}
