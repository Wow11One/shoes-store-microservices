FROM ghcr.io/graalvm/jdk:ol7-java17 as graalvm
COPY authentication-service/target/authentication-service-0.1.jar app.jar
ENV JAVA_OPTS=""
CMD ["java", "-jar", "app.jar"]