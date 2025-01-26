FROM openjdk:21-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN bash -c 'touch /app.jar'

COPY opentelemetry /opentelemetry

ENTRYPOINT ["java", "-javaagent:opentelemetry/opentelemetry-javaagent.jar", "-Dotel.resource.attributes=service.name=checkout-api", "-Dotel.exporter.otlp.endpoint=http://jaeger:4318" , "-Dotel.traces.exporter=otlp" , "-Dotel.metrics.exporter=none", "-Dotel.logs.exporter=none", "-Dotel.exporter.otlp.protocol=http/protobuf", "-jar", "/app.jar"]