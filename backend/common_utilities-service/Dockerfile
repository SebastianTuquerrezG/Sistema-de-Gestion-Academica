# Dockerfile para common_utilities-service
FROM openjdk:21-jdk-slim
WORKDIR /app
ENV SPRING_PROFILES_ACTIVE=docker
COPY target/common_utilities-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]