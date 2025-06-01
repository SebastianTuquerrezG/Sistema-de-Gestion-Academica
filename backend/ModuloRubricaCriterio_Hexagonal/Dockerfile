# Imagen base con Java y Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build


WORKDIR /app

COPY . .

RUN mvn clean package -pl ModuloRubricaCriterio -am

# Dockerfile para eureka-server
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY /app/ModuloRubricaCriterio_Hexagonal/target/ModuloRubricaCriterio-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 5001
ENTRYPOINT ["java", "-jar", "app.jar"]