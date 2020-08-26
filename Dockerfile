FROM openjdk:11-slim

WORKDIR /usr/src/partners

ARG JAR_FILE=target/partners-service*.jar
COPY ${JAR_FILE} partners.jar

ENTRYPOINT ["java", "-jar","partners.jar"]

EXPOSE 8080