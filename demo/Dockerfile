FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY ./target/demo-0.0.1-SNAPSHOT.jar app.jar
COPY ./jars/opentelemetry-javaagent-1.32.0.jar opentelemetry-javaagent.jar
ENTRYPOINT ["java","-javaagent:opentelemetry-javaagent.jar", "-jar", "app.jar"]
