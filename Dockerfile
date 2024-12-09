FROM maven:3.9.5-eclipse-temurin-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
RUN ls -la
RUN pwd

FROM openjdk:17-jdk-slim
WORKDIR /app
RUN ls -la
COPY --from=build /app/web/target/*.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/application.jar"]
