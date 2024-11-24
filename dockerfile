FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/Semi-Automated-LIMS-0.0.1-SNAPSHOT.jar app.jar
COPY application.properties /app/application.properties
ENV SPRING_CONFIG_LOCATION=file:/app/application.properties
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

