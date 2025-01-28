FROM openjdk:21-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN bash -c 'touch /app.jar'

COPY opentelemetry /opentelemetry

ENTRYPOINT ["java", "-javaagent:opentelemetry/opentelemetry-javaagent.jar", "-Dotel.resource.attributes=service.name=checkout-api", "-Dotel.traces.exporter=zipkin", "-Dotel.exporter.zipkin.endpoint=http://zipkin:9411/api/v2/spans", "-Dotel.metrics.exporter=none", "-Dotel.logs.exporter=none", "-jar", "/app.jar"]