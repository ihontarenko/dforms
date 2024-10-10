package df.base.mapping.reflection;

import df.base.common.mapping.Mapper;
import df.base.dto.reflection.MethodDTO;

import java.lang.reflect.Method;

public class MethodMapper implements Mapper<Method, MethodDTO> {

    @Override
    public MethodDTO map(Method source) {
        MethodDTO methodDTO = new MethodDTO();

        methodDTO.setName(source.getName());
        methodDTO.setNativeMethod(source);
        methodDTO.setNativeClass(source.getDeclaringClass());

        return methodDTO;
    }

    @Override
    public Method reverse(MethodDTO source) {
        throw new UnsupportedOperationException();
    }

}
