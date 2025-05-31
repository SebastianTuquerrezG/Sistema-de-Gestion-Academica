# Imagen base con Java y Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar archivos del proyecto
COPY . .

# Compilar el proyecto y generar el JAR
RUN mvn clean package

# Dockerfile para gateway
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/gateway-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
