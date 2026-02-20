package df.application.service.bc;

import df.application.dto.reflection.ClassListDTO;
import org.jmouse.core.matcher.Matcher;
import org.jmouse.core.reflection.ClassMatchers;
import org.jmouse.core.reflection.Reflections;
import df.application.dto.reflection.ClassDTO;
import df.application.old_mapping.reflection.ClassMapper;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.AbstractCollection;
import java.util.Collection;

@Service
public class ClassService {

    private final ClassRepository repository;
    private final ClassMapper     classMapper = new ClassMapper();

    public ClassService(ClassRepository repository) {
        this.repository = repository;
    }

    public ClassDTO getClass(String className) {
        return classMapper.map(Reflections.getClassFor(className));
    }

    public ClassListDTO findAnnotatedClasses(Class<? extends Annotation> annotation) {
        return mapClasses(repository.findClasses(ClassMatchers.isAnnotatedWith(annotation)));
    }

    public ClassListDTO findClassesByName(String name) {
        return mapClasses(repository.findClasses(name == null ? Matcher.constant(false) : ClassMatchers.nameContains(name)));
    }

    public ClassListDTO findAnnotations() {
        return mapClasses(repository.findClasses(ClassMatchers.isAnnotation()));
    }

    public ClassListDTO findPublicClasses() {
        return mapClasses(repository.findClasses(ClassMatchers.isPublic()));
    }

    public ClassListDTO findEnums() {
        return mapClasses(repository.findClasses(ClassMatchers.isEnum()));
    }

    public ClassListDTO findImplementations(Class<?> interfaceClass) {
        return mapClasses(repository.findClasses(ClassMatchers.implementsInterface(interfaceClass).and(ClassMatchers.isAbstract().not())));
    }

    public ClassListDTO mapClasses(Collection<Class<?>> classes) {
        return classes.stream().map(classMapper::map).collect(ClassListDTO::new, Collection::add, AbstractCollection::addAll);
    }

}
