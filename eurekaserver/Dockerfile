FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY ./target/eurekaserver 0.0.1-SNAPSHOT app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
