package df.base.mapping.reflection;

import df.base.common.mapping.Mapper;
import df.base.dto.reflection.MethodDTO;

import java.lang.reflect.Method;
import java.util.stream.Stream;

public class MethodMapper implements Mapper<Method, MethodDTO> {

    private static final ClassMapper CLASS_MAPPER = new ClassMapper();

    @Override
    public MethodDTO map(Method source) {
        MethodDTO methodDTO = new MethodDTO();

        methodDTO.setDeclaringClass(CLASS_MAPPER.map(source.getDeclaringClass()));
        methodDTO.setName(source.getName());
        methodDTO.setNativeMethod(source);
        methodDTO.setNativeClass(source.getDeclaringClass());
        methodDTO.setReturnType(CLASS_MAPPER.map(source.getReturnType()));

        Stream.of(source.getParameterTypes()).map(CLASS_MAPPER::map)
                .forEach(methodDTO::addParameterType);

        return methodDTO;
    }

    @Override
    public Method reverse(MethodDTO source) {
        throw new UnsupportedOperationException();
    }

}
