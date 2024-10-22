package df.base.service;

import df.base.common.matcher.Matcher;
import df.base.common.matcher.reflection.ClassMatchers;
import df.base.dto.reflection.ClassDTO;
import df.base.mapping.reflection.ClassMapper;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Service
public class ClassService {

    private final ClassRepository repository;
    private final ClassMapper     classMapper = new ClassMapper();

    public ClassService(ClassRepository repository) {
        this.repository = repository;
    }

    public Set<ClassDTO> findAnnotatedClasses(Class<? extends Annotation> annotation) {
        return mapClasses(repository.findClasses(ClassMatchers.isAnnotatedWith(annotation)));
    }

    public Set<ClassDTO> findClassesByName(String name) {
        return mapClasses(repository.findClasses(name == null ? Matcher.constant(false) : ClassMatchers.nameContains(name)));
    }

    public Set<ClassDTO> findAnnotations() {
        return mapClasses(repository.findClasses(ClassMatchers.isAnnotation()));
    }

    public Set<ClassDTO> findPublicClasses() {
        return mapClasses(repository.findClasses(ClassMatchers.isPublic()));
    }

    public Set<ClassDTO> findEnums() {
        return mapClasses(repository.findClasses(ClassMatchers.isEnum()));
    }

    public Set<ClassDTO> findImplementations(Class<?> interfaceClass) {
        return mapClasses(repository.findClasses(ClassMatchers.implementsInterface(interfaceClass).and(ClassMatchers.isAbstract().not())));
    }

    public Set<ClassDTO> mapClasses(Set<Class<?>> classes) {
        return classes.stream().map(classMapper::map).collect(toSet());
    }

    public Map<String, Set<ClassDTO>> groupedClasses(Set<ClassDTO> classes, Function<? super ClassDTO, String> classifier) {
        return classes.stream().collect(Collectors.groupingBy(classifier, Collectors.toSet()));
    }

}
