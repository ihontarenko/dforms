
# Matcher Utility Library

This library provides a set of flexible and powerful matchers that can be used to perform complex matching operations on Java classes, methods, fields, collections, types, paths, and more. The library is designed with a fluent API and can be easily extended for different use cases.

## Overview

The library contains the following key components:

- **ClassMatchers**: For matching classes based on modifiers, annotations, methods, fields, and interfaces.
- **FieldMatchers**: For matching fields in classes by modifiers, annotations, and names.
- **MethodMatchers**: For matching methods and constructors based on modifiers, annotations, return types, parameter types, etc.
- **TypeMatchers**: For matching types by similarity or subtype relationships (e.g., primitive vs. wrapper types).
- **CollectionMatchers**: For matching collections based on their contents.
- **PathMatchers**: For matching `Path` objects based on file system properties.
- **TextMatchers**: For matching strings based on patterns like starts with, ends with, contains, etc.
- **Matcher**: A core interface for all matchers.
- **MatchContext**: A context passed during matching operations to store auxiliary data.

## Usage Examples

### 1. Matching Classes with `ClassMatchers`

You can use `ClassMatchers` to check class-level properties like modifiers, implemented interfaces, and annotations.

```java
import df.base.common.matcher.reflection.ClassMatchers;

Class<?> clazz = MyClass.class;

// Check if the class is public
boolean isPublic = ClassMatchers.isPublic().matches(clazz, MatchContext.createDefault());

// Check if the class implements a specific interface
boolean implementsList = ClassMatchers.implementsInterface(List.class).matches(clazz, MatchContext.createDefault());

// Check if the class is annotated with @Deprecated
boolean isDeprecated = ClassMatchers.isAnnotatedWith(Deprecated.class).matches(clazz, MatchContext.createDefault());
```

### 2. Matching Fields with `FieldMatchers`

Use `FieldMatchers` to match fields based on modifiers, types, annotations, and names.

```java
import df.base.common.matcher.reflection.FieldMatchers;

Field field = MyClass.class.getDeclaredField("myField");

// Check if the field is public
boolean isPublic = FieldMatchers.isPublic().matches(field, MatchContext.createDefault());

// Check if the field is of type String
boolean isStringField = FieldMatchers.hasType(String.class).matches(field, MatchContext.createDefault());

// Check if the field is annotated with @Deprecated
boolean isDeprecatedField = FieldMatchers.isAnnotatedWith(Deprecated.class).matches(field, MatchContext.createDefault());
```

### 3. Matching Methods and Constructors with `MethodMatchers`

You can match methods and constructors using `MethodMatchers`, which support matching based on modifiers, return types, parameter types, and annotations.

```java
import df.base.common.matcher.reflection.MethodMatchers;
import java.lang.reflect.Method;

Method method = MyClass.class.getDeclaredMethod("myMethod");

// Check if the method is public
boolean isPublic = MethodMatchers.isPublic().matches(method, MatchContext.createDefault());

// Check if the method has a return type of String
boolean returnsString = MethodMatchers.hasReturnType(String.class).matches(method, MatchContext.createDefault());

// Check if the method is annotated with @Override
boolean isOverridden = MethodMatchers.isAnnotatedWith(Override.class).matches(method, MatchContext.createDefault());
```

### 4. Type Matching with `TypeMatchers`

Use `TypeMatchers` to compare types for similarity (e.g., `int` vs. `Integer`) or for subtype relationships.

```java
import df.base.common.matcher.reflection.TypeMatchers;

Class<?> actualType = Integer.class;

// Check if the type is similar to int (i.e., matches int and Integer)
boolean isSimilar = TypeMatchers.isSimilar(int.class).matches(actualType, MatchContext.createDefault());

// Check if the type is a subtype of Number
boolean isSubtype = TypeMatchers.isSubtype(Number.class).matches(actualType, MatchContext.createDefault());
```

### 5. Matching Collections with `CollectionMatchers`

`CollectionMatchers` allow you to match collections based on the contents.

```java
import df.base.common.matcher.collections.CollectionMatchers;

List<String> list = Arrays.asList("apple", "banana", "cherry");

// Check if the collection contains a specific element
boolean containsApple = CollectionMatchers.contains("apple").matches(list, MatchContext.createDefault());

// Check if all elements in the collection match a condition
boolean allStartWithA = CollectionMatchers.allMatch(TextMatchers.startsWith("a")).matches(list, MatchContext.createDefault());
```

### 6. Matching Paths with `PathMatchers`

You can use `PathMatchers` to match file system paths based on properties like existence, readability, or specific extensions.

```java
import df.base.common.matcher.PathMatchers;
import java.nio.file.Path;
import java.nio.file.Paths;

Path path = Paths.get("/home/user/file.txt");

// Check if the file exists
boolean exists = PathMatchers.exists().matches(path, MatchContext.createDefault());

// Check if the path is a directory
boolean isDirectory = PathMatchers.isDirectory().matches(path, MatchContext.createDefault());

// Check if the file has a ".txt" extension
boolean hasTxtExtension = PathMatchers.hasExtension("txt").matches(path, MatchContext.createDefault());
```

### 7. Matching Text with `TextMatchers`

`TextMatchers` allow you to perform matching on strings using different patterns.

```java
import df.base.common.matcher.TextMatchers;

String input = "HelloWorld";

// Check if the string starts with "Hello"
boolean startsWithHello = TextMatchers.startsWith("Hello").matches(input, MatchContext.createDefault());

// Check if the string contains "World"
boolean containsWorld = TextMatchers.contains("World").matches(input, MatchContext.createDefault());

// Check if the string equals "HelloWorld"
boolean isEqual = TextMatchers.same("HelloWorld").matches(input, MatchContext.createDefault());
```

## Core Interfaces and Context

### `Matcher`

The `Matcher<T>` interface is the core interface for all matchers. It allows matching objects of type `T` against certain conditions.

### `MatchContext`

`MatchContext` is used to provide additional data to matchers during the matching process. It can store auxiliary data for more complex matching operations.

```java
MatchContext context = MatchContext.createDefault();
```

---

## Example Usage

Below is an example of combining various matchers to perform complex matching.

```java
import df.base.common.matcher.reflection.ClassMatchers;
import df.base.common.matcher.reflection.FieldMatchers;
import df.base.common.matcher.reflection.MethodMatchers;
import df.base.common.matcher.MatchContext;

Class<?> clazz = MyClass.class;

// Match a class that is public, implements List, and has a field named "data" of type String
boolean matches = ClassMatchers.isPublic()
    .and(ClassMatchers.implementsInterface(List.class))
    .and(ClassMatchers.hasField(FieldMatchers.nameEquals("data").and(FieldMatchers.hasType(String.class))))
    .matches(clazz, MatchContext.createDefault());
```

## Contributing

If you'd like to contribute to this library, feel free to submit pull requests or open issues on the repository. We welcome any contributions or improvements.

## License

This project is licensed under the MIT License.
