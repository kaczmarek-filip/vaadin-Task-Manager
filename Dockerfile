#FROM ubuntu:latest AS build
#RUN apt-get update
#RUN apt-get install openjdk-17-jdk -y
#COPY . .
#RUN chmod +x ./gradlew
#RUN ./gradlew bootJar --no-daemon
#
#FROM openjdk:17-jdk-slim
#EXPOSE 8080
#COPY --from=build /target/vaadin-1.0-SNAPSHOT.jar app.jar
#
#ENTRYPOINT ["java", "-jar", "app.jar"]
# Użyj oficjalnej wersji Mavena jako obrazu bazowego
FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean package
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/vaadin-1.0-SNAPSHOT.jar ./app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
