
socket-app (filees inside are)
src/main/java/com/socketapp/
  Server.java
  Client.java
pom.xml
src/test/java/com/socketapp/AppTest.java

Server.java
package com.socketapp;
import java.io.*;
import java.net.*;
public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server started...");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            String message = in.readLine();
            System.out.println("Received: " + message);
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Client.java
package com.socketapp;
import java.io.*;
import java.net.*;
public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Hello from Client");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

pom.xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.socketapp</groupId>
    <artifactId>socket-app</artifactId>
    <version>1.0-SNAPSHOT</version>
    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.10.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.0.0</version>
            </plugin>
        </plugins>
    </build>
</project>

AppTest.java
package com.socketapp;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class AppTest {
    @Test
    void testSample() {
        assertTrue(true);
    }
}

TERMINAL-> mvn clean compile (then) mvn test

TERMINAL-> cd src/main/java , javac com/socketapp/Server.java , java com.socketapp.Server
IN ANOTHER -> cd src/main/java , javac com/socketapp/Client.java , java com.socketapp.Client

GIT CREATE + ADD IN TERMINAL ALL COMMANDS
git remote add origin https://github.com/your-username/socket-app.git
git branch -M main
git push -u origin main

PIPELINE SCRIPT
pipeline {
    agent any
    stages {
        stage('Clone') {
            steps {
                git branch: 'main',
                url: 'https://github.com/Srilaya18/socket-app.git'
            }
        }
        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
        stage('Package') {
            steps {
                bat 'mvn package'
            }
        }
    }
}

Dockerfile
FROM eclipse-temurin:17
WORKDIR /app
COPY target/socket-app-1.0-SNAPSHOT.jar app.jar
CMD ["java", "-cp", "app.jar", "com.socketapp.Server"]

PUSH TO GIT
git add .
git commit -m "Added Dockerfile and pipeline"
git push

PIPELINE CHANGE AGAIN
pipeline {
    agent any
    stages {
        stage('Clone') {
            steps {
                git branch: 'main',
                url: 'https://github.com/Srilaya18/socket-app.git'
            }
        }
        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
        stage('Package') {
            steps {
                bat 'mvn package'
            }
        }
        stage('Docker Build') {
            steps {
                bat 'docker build -t socket-app .'
            }
        }
        stage('Docker Run') {
            steps {
                bat 'docker run -d -p 5000:5000 socket-app'
            }
        }
    }
}

SHOW IMAGE IN DOCKER -> run get CONTAINER

THEN IN TERMINAL
docker images
docker ps
docker logs <container_id>
cd src/main/java
java com.socketapp.Client
docker logs <container_id>
