package df.base.mapping.reflection;

import df.base.common.mapping.Mapper;
import df.base.dto.reflection.MethodDTO;

import java.lang.reflect.Method;
import java.util.stream.Stream;

public class MethodMapper implements Mapper<Method, MethodDTO> {

    private final ClassMapper classMapper = new ClassMapper();

    @Override
    public MethodDTO map(Method source) {
        MethodDTO methodDTO = new MethodDTO();

        methodDTO.setDeclaringClass(classMapper.map(source.getDeclaringClass()));
        methodDTO.setName(source.getName());
        methodDTO.setNativeMethod(source);
        methodDTO.setNativeClass(source.getDeclaringClass());

        Stream.of(source.getParameterTypes()).map(classMapper::map)
                .forEach(methodDTO::addParameterType);

        return methodDTO;
    }

    @Override
    public Method reverse(MethodDTO source) {
        throw new UnsupportedOperationException();
    }

}
