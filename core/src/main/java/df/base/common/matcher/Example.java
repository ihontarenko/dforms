package df.base.common.matcher;

import df.base.common.invocable.ReflectionMethodDescriptor;
import df.base.common.mapping.mapper.ObjectFieldMapper;
import df.base.common.matcher.reflection.MethodMatchers;
import df.base.common.reflection.MethodFinder;
import df.base.common.reflection.Reflections;
import df.base.persistence.repository.form.FieldRepository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Example {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MethodFinder finder = new MethodFinder();

        for (Method method : finder.find(FieldRepository.class, MethodMatchers.nameStarts("find"))) {
            System.out.println(new ReflectionMethodDescriptor(method, method.getDeclaringClass()).getName());
        }

        Reflections.getInterfacesParameterizedTypes(ObjectFieldMapper.class);
    }
}

