package df.application.service.bc;

import df.application.dto.reflection.ClassSetDTO;
import df.common.matcher.Matcher;
import df.common.matcher.reflection.ClassMatchers;
import df.common.reflection.Reflections;
import df.application.dto.reflection.ClassDTO;
import df.application.mapping.reflection.ClassMapper;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.AbstractCollection;
import java.util.Set;

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

    public ClassSetDTO findAnnotatedClasses(Class<? extends Annotation> annotation) {
        return mapClasses(repository.findClasses(ClassMatchers.isAnnotatedWith(annotation)));
    }

    public ClassSetDTO findClassesByName(String name) {
        return mapClasses(repository.findClasses(name == null ? Matcher.constant(false) : ClassMatchers.nameContains(name)));
    }

    public ClassSetDTO findAnnotations() {
        return mapClasses(repository.findClasses(ClassMatchers.isAnnotation()));
    }

    public ClassSetDTO findPublicClasses() {
        return mapClasses(repository.findClasses(ClassMatchers.isPublic()));
    }

    public ClassSetDTO findEnums() {
        return mapClasses(repository.findClasses(ClassMatchers.isEnum()));
    }

    public ClassSetDTO findImplementations(Class<?> interfaceClass) {
        return mapClasses(repository.findClasses(ClassMatchers.implementsInterface(interfaceClass).and(ClassMatchers.isAbstract().not())));
    }

    public ClassSetDTO mapClasses(Set<Class<?>> classes) {
        return classes.stream().map(classMapper::map).collect(ClassSetDTO::new, Set::add, AbstractCollection::addAll);
    }

}
