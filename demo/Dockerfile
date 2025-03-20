FROM k8s-vga-worker1:5000/openjdk:21-jdk-slim as build
WORKDIR /app
COPY ./target/demo-0.0.1-SNAPSHOT.jar app.jar
COPY ./jars/opentelemetry-javaagent-1.32.0.jar opentelemetry-javaagent.jar
ENTRYPOINT ["java","-javaagent:/app/opentelemetry-javaagent.jar","-jar", "app.jar"]
