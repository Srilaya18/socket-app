FROM openjdk:17
WORKDIR /app
COPY target/socket-app-1.0-SNAPSHOT.jar app.jar
CMD ["java", "-cp", "app.jar", "com.socketapp.Server"]