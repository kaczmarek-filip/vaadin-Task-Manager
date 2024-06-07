FROM openjdk:17-jdk-slim
COPY target/*.jar app.jar
COPY src/main/resources/encryptionKeys.json src/main/resources/encryptionKeys.json
COPY src/main/resources/hibernate.cfg.xml src/main/resources/hibernate.cfg.xml
EXPOSE 8080

ENV DATABASE_PASSWORD=AVNS_fleYHV9Km-huDyti7EB

ENTRYPOINT ["java", "-jar", "/app.jar"]