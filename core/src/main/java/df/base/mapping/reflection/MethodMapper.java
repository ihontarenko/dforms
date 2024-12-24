package df.base.mapping.reflection;

import df.base.common.mapping.Mapper;
import df.base.common.mapping.Mapping;
import df.base.dto.reflection.ClassDTO;
import df.base.dto.reflection.MethodDTO;

import java.lang.reflect.Method;
import java.util.stream.Stream;

public class MethodMapper implements Mapper<Method, MethodDTO> {

    @Override
    public MethodDTO map(Method source) {
        MethodDTO methodDTO = new MethodDTO();

        methodDTO.setDeclaringClass(Mapping.INSTANCE.map(source.getDeclaringClass()));
        methodDTO.setName(source.getName());
        methodDTO.setNativeMethod(source);
        methodDTO.setNativeClass(source.getDeclaringClass());
        methodDTO.setReturnType(Mapping.INSTANCE.map(source.getReturnType()));

        Stream.of(source.getParameterTypes())
                .map(Mapping.INSTANCE::map).map(ClassDTO.class::cast)
                .forEach(methodDTO::addParameterType);

        return methodDTO;
    }

    @Override
    public Method reverse(MethodDTO source) {
        throw new UnsupportedOperationException();
    }

}
