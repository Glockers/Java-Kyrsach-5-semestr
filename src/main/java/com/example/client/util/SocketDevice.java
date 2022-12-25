package com.example.client.util;

import com.example.client.DTO.MessageRequest;
import com.example.client.DTO.MessageResponse;
import com.example.client.type.RequestType;
import com.example.client.type.ResponseType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketDevice {
    Gson gson = new Gson();
    private final static int PORT = 8080;
    private final static String HOST = "localhost";
    private static SocketDevice INSTANCE = null;
    private Socket socket = null;

    private BufferedReader in = null;

    public PrintWriter getOut() {
        return out;
    }

    private PrintWriter out = null;



    public BufferedReader getIn() {
        return in;
    }

    public Socket getSocket() {
        return socket;
    }

    private SocketDevice() throws IOException {
        this.socket = new Socket(HOST, PORT);
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    public static SocketDevice getInstance() throws IOException {
        if (INSTANCE == null) {
            INSTANCE = new SocketDevice();
        }
        return INSTANCE;
    }




}
