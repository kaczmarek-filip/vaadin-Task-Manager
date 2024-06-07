#FROM maven:latest AS build
#WORKDIR /app
#COPY . .
#RUN mvn clean package vaadin:build-frontend
#FROM openjdk:17-jdk-slim
#WORKDIR /app
#COPY --from=build /app/target/vaadin-1.0-SNAPSHOT.jar ./app.jar
#COPY src/main/resources/encryptionKeys.json src/main/resources/encryptionKeys.json
##COPY frontend/ frontend/
##COPY --from=build /app/src/main/resources/ /app/src/main/resources/
##COPY --from=build /app/target/ ./app/src/main/resources/
#COPY frontend/ /app/frontend
#EXPOSE 8080
#
#ENV DATABASE_PASSWORD=AVNS_fleYHV9Km-huDyti7EB
#
#ENTRYPOINT ["java", "-jar", "app.jar"]
#FROM maven:latest
#RUN mvn clean package -Pproduction
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src src
RUN mvn clean package -Pproduction

FROM openjdk:17-jdk-slim
COPY target/*.jar app.jar
COPY src/main/resources/encryptionKeys.json src/main/resources/encryptionKeys.json
COPY src/main/resources/hibernate.cfg.xml src/main/resources/hibernate.cfg.xml
EXPOSE 8080

ENV DATABASE_PASSWORD=AVNS_fleYHV9Km-huDyti7EB

ENTRYPOINT ["java", "-jar", "/app.jar"]