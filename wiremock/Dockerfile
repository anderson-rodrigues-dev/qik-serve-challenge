FROM openjdk:21-jdk

ARG JAR_FILE=*.jar
COPY ${JAR_FILE} /app.jar
RUN bash -c 'touch /app.jar'

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app.jar"]
