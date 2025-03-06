FROM openjdk:21-jdk

WORKDIR /app

COPY target/app.jar app.jar

EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
