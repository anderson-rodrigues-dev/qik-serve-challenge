FROM openjdk:21-jdk

ARG JAR_FILE=*.jar
COPY ${JAR_FILE} /app.jar
RUN bash -c 'touch /app.jar'

COPY mappings /mappings

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app.jar", "--port", "8081", "--bind-address", "0.0.0.0", "--verbose"]
