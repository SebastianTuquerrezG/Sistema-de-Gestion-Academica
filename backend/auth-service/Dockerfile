# Dockerfile para auth-service
FROM openjdk:21-jdk-slim
WORKDIR /app
EXPOSE 9090
COPY target/auth-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]