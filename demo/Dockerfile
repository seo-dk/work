FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY ./target/demo-0.0.1-SNAPSHOT.jar app.jar
COPY ./jars/opentelemetry-javaagent-2.13.0.jar opentelemetry-javaagent.jar
ENTRYPOINT ["java","-javaagent:/app/opentelemetry-javaagent.jar","-jar", "app.jar"]
