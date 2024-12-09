FROM maven:3.9.5-eclipse-temurin-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/web/target/*.jar application.jar
EXPOSE 8099
ENTRYPOINT ["java", "-jar", "/app/application.jar"]
