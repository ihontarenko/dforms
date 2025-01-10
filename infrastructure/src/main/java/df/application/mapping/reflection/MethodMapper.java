package df.application.mapping.reflection;

import df.application.Instances;
import df.application.dto.reflection.ClassDTO;
import df.application.dto.reflection.MethodDTO;
import svit.mapping.Mapper;
import svit.mapping.Mapping;

import java.lang.reflect.Method;
import java.util.stream.Stream;

public class MethodMapper implements Mapper<Method, MethodDTO> {

    @Override
    public MethodDTO map(Method source) {
        MethodDTO methodDTO = new MethodDTO();
        Mapping   mapping   = Instances.MAPPING;

        methodDTO.setDeclaringClass(mapping.map(source.getDeclaringClass()));
        methodDTO.setName(source.getName());
        methodDTO.setNativeMethod(source);
        methodDTO.setNativeClass(source.getDeclaringClass());
        methodDTO.setReturnType(mapping.map(source.getReturnType()));

        Stream.of(source.getParameterTypes())
                .map(mapping::map).map(ClassDTO.class::cast)
                .forEach(methodDTO::addParameterType);

        return methodDTO;
    }

    @Override
    public Method reverse(MethodDTO source) {
        throw new UnsupportedOperationException();
    }

}
