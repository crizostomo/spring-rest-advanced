FROM openjdk:11-jre-slim

WORKDIR /app

COPY build/libs/*.jar /app/api.jar

EXPOSE 8080

CMD ["java", "-jar", "api.jar"]