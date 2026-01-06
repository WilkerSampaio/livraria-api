FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY build/libs/livraria-api-0.0.1-SNAPSHOT.jar /app/livraria-api.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/livraria-api.jar"]