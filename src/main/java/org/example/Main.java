package org.example;

import org.example.client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main {

    private static int PORT;

    private static ServerSocket serverSocket;
    private static ClientHandler clientHandler;

    public static void main(String[] args) {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("server", Locale.getDefault());
            PORT = Integer.parseInt(resourceBundle.getString("server.port"));
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server start on PORT: " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientHandler = new ClientHandler(clientSocket);
                Thread thread = new Thread(clientHandler);
                thread.start();
                System.out.println("New client connected " + clientSocket.getInetAddress().getHostAddress());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}