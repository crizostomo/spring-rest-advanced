FROM openjdk:11-jre-slim

WORKDIR /app

ARG JAR_FILE

# COPY build/libs/*.jar /app/api.jar
COPY build/libs/${JAR_FILE} /app/api.jar

EXPOSE ${PORT}

CMD ["java", "-jar", "api.jar"]