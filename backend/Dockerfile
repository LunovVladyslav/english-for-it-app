# Build Stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Skip tests to speed up build in container, assuming tests ran in CI
RUN mvn clean package -DskipTests

# Run Stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Environment variables (can be overridden)
ENV SPRING_PROFILES_ACTIVE=docker

# Run
ENTRYPOINT ["java", "-jar", "app.jar"]
