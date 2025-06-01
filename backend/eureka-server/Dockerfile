FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY . .

RUN mvn clean package -pl eureka-server -am

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]
