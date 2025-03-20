FROM k8s-vga-worker1:5000/openjdk:21-jdk-slim as build
WORKDIR /app
COPY ./target/gatewayserver-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
