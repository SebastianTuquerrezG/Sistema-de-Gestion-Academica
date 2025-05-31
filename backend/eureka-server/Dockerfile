# Imagen base con Java y Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar archivos del proyecto
COPY . .

# Compilar el proyecto y generar el JAR
RUN mvn clean package

# Usar una imagen más ligera para ejecutar el servicio
FROM openjdk:21-jdk-slim

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el JAR compilado desde la fase anterior
COPY --from=build /app/target/eureka-server-0.0.1-SNAPSHOT.jar ./app.jar

# Exponer el puerto del servicio
EXPOSE 8761

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]