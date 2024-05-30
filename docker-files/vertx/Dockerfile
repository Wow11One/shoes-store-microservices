FROM ghcr.io/graalvm/jdk:ol7-java17 as graalvm
COPY mail-service/target/mail-service-1.0.0-SNAPSHOT-fat.jar app.jar
ENV JAVA_OPTS=""
CMD ["java", "-jar", "app.jar"]