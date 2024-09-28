package df.base.common.matcher;

import df.base.common.matcher.reflection.MethodMatchers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static df.base.common.matcher.MatchContext.createDefault;

public class Example {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MatchContext context = createDefault();

        class MyClass {
            private int value;

            // Копіюючий конструктор
            public MyClass(MyClass other) {
                this.value = other.value;
            }

            // Інший конструктор
            public MyClass(int value) {
                this.value = value;
            }

            // Метод для демонстрації (не використовується для перевірки)
            public void setValue(int value) {
                this.value = value;
            }
        }

        // Отримуємо всі конструктори класу MyClass
        Constructor<?>[] constructors = MyClass.class.getDeclaredConstructors();

        // Створюємо наш матчер для копіюючого конструктора
        Matcher<Constructor<?>> copyConstructorMatcher = MethodMatchers.isCopyConstructor();

        // Перевіряємо кожен конструктор
        for (Constructor<?> constructor : constructors) {
            boolean isCopyConstructor = copyConstructorMatcher.matches(constructor, context);
            System.out.println("Constructor: " + constructor);
            System.out.println("Is Copy Constructor: " + isCopyConstructor);
        }


    }
}

