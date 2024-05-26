FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean package
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/vaadin-1.0-SNAPSHOT.jar ./app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

FROM mysql:latest AS db
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=taskmanagergenerated
#ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=mysql

EXPOSE 3306
COPY taskmanagergenerated.sql.sql /docker-entrypoint-initdb.d/

# Użyj obrazu MySQL jako serwera bazy danych
#FROM mysql:latest
#COPY --from=db /var/lib/mysql /var/lib/mysql