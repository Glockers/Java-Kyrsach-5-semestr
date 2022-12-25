package org.example.client;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import com.google.gson.Gson;
import org.example.controller.RequestController;
import org.example.types.ResponseType;

public class ClientHandler implements Runnable {

    private Socket clientSocket;

    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);

    }

    @Override
    public void run() {
        try {
            RequestController controller = new RequestController();
            while (clientSocket.isConnected()) {
                String msg = in.readLine();
                System.out.println("Получен запрос от клиента: " + msg);
                String response = controller.getResponse(msg);
                if (response != null){
                    out.println(response);
                }else{
                    out.println(new Gson().toJson(ResponseType.ERROR));
                }
            }
        } catch (SocketException e) {
            try {
                System.out.println("Клиент отключился от сервера");
                clientSocket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}