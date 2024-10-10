package df.base.service;

import df.base.common.matcher.reflection.ClassMatchers;
import df.base.dto.reflection.ClassDTO;
import df.base.mapping.reflection.ClassMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClassService {

    private final ClassRepository repository;

    public ClassService(ClassRepository repository) {
        this.repository = repository;
    }

    public Set<ClassDTO> findEnums() {
        return repository.findClasses(ClassMatchers.isEnum()).stream().map(new ClassMapper()::map)
                .collect(Collectors.toSet());
    }

    public Set<ClassDTO> findImplementations(Class<?> interfaceClass) {
        return repository.findClasses(ClassMatchers.implementsInterface(interfaceClass).not()).stream()
                .map(new ClassMapper()::map).collect(Collectors.toSet());
    }

}
