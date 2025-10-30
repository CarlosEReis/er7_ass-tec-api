FROM openjdk:17-jdk-slim
EXPOSE 8080
ARG JAR_FILE=target/assistec-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
