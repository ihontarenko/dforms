package df.base.service.bc;

import df.base.common.matcher.Matcher;
import df.base.common.matcher.reflection.ClassMatchers;
import df.base.common.reflection.Reflections;
import df.base.dto.reflection.ClassDTO;
import df.base.dto.reflection.ClassSetDTO;
import df.base.dto.reflection.MethodDTO;
import df.base.dto.reflection.MethodSetDTO;
import df.base.mapping.reflection.ClassMapper;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.AbstractCollection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public Map<String, ClassSetDTO> groupedClasses(ClassSetDTO classes, Function<? super ClassDTO, String> classifier) {
        return classes.stream().collect(Collectors.groupingBy(classifier, Collectors.toCollection(ClassSetDTO::new)));
    }

    public Map<String, MethodSetDTO> groupedMethods(MethodSetDTO methods, Function<? super MethodDTO, String> classifier) {
        return methods.stream().collect(Collectors.groupingBy(classifier, Collectors.toCollection(MethodSetDTO::new)));
    }

}
