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