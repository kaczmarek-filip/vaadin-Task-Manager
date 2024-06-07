FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean package vaadin:build-frontend
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/vaadin-1.0-SNAPSHOT.jar ./app.jar
COPY src/main/resources/encryptionKeys.json src/main/resources/encryptionKeys.json
#COPY frontend/ frontend/
#COPY --from=build /app/src/main/resources/ /app/src/main/resources/
#COPY --from=build /app/target/ ./app/src/main/resources/
COPY --from=build /app/frontend/ ./app/frontend
EXPOSE 8080

ENV DATABASE_PASSWORD=AVNS_fleYHV9Km-huDyti7EB

ENTRYPOINT ["java", "-jar", "app.jar"]