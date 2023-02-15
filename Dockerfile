FROM openjdk:11-jre-slim

WORKDIR /app

ARG JAR_FILE

# COPY build/libs/*.jar /app/api.jar
COPY build/libs/${JAR_FILE} /app/api.jar
# COPY wait-for-it.sh /wait-for-it.sh

# RUN chmod +x /wait-for-it.sh

EXPOSE ${PORT}

CMD ["java", "-jar", "api.jar"]