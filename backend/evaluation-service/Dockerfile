# Dockerfile para eureka-server
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/evaluation-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 5002
ENTRYPOINT ["java", "-jar", "app.jar"]