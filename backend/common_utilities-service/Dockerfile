# Dockerfile para helper-microservice
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/common_utilities-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]