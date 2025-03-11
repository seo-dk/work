FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY ./target/demo-0.0.1-SNAPSHOT.jar app.jar
COPY ./jars/opentelemetry-javaagent-1.32.0.jar opentelemetry-javaagent.jar
ENTRYPOINT ["java",\
"-javaagent:/app/opentelemetry-javaagent.jar", \
"-Dotel.traces.exporter=jaeger", \
"-Dotel.exporter.jaeger.endpoint=http://team8-jaeger-collector:14268/api/traces", \
"-Dotel.service.name=demo-service", \
"-jar", "app.jar"]
