# Dockerfile para eureka-server
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/ModuloRubricaCriterio-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 5001
ENTRYPOINT ["java", "-jar", "app.jar"]