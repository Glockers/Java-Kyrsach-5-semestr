package com.example.client.util;

import com.example.client.DTO.MessageRequest;
import com.example.client.DTO.MessageResponse;
import com.example.client.type.RequestType;
import com.example.client.type.ResponseType;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


public class SocketService {

    Gson gson = new Gson();
    private PrintWriter out = null;
    private BufferedReader in = null;

    public SocketService() {
        try {
            this.out = SocketDevice.getInstance().getOut();
            this.in = SocketDevice.getInstance().getIn();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public MessageResponse doGet(RequestType requestType) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            MessageRequest messageRequest = new MessageRequest();
            messageRequest.setRequestType(requestType);
            System.out.println("GET METHOD SEND: " + gson.toJson(messageRequest));
            out.println(gson.toJson(messageRequest));

            String responseMsg = in.readLine();
            System.out.println("GET METHOD RECV: " + responseMsg);
            messageResponse = gson.fromJson(responseMsg, MessageResponse.class);
        } catch (IOException e) {
            messageResponse.setResponseType(ResponseType.ERROR_CLIENT);
            throw new RuntimeException(e);
        }
        return messageResponse;
    }


    public MessageResponse doGet(RequestType requestType, Object object) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            MessageRequest messageRequest = new MessageRequest();
            messageRequest.setRequestType(requestType);
            messageRequest.setPayload(gson.toJson(object));
            System.out.println("GET METHOD SEND: " + gson.toJson(messageRequest));
            out.println(gson.toJson(messageRequest));

            String responseMsg = in.readLine();
            System.out.println("GET METHOD RECV: " + responseMsg);
            messageResponse = gson.fromJson(responseMsg, MessageResponse.class);
        } catch (IOException e) {
            messageResponse.setResponseType(ResponseType.ERROR_CLIENT);
            throw new RuntimeException(e);
        }
        return messageResponse;
    }

    public MessageResponse doUpdate(RequestType requestType, Object obj) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            MessageRequest message = new MessageRequest();

            message.setRequestType(requestType);
            message.setPayload(gson.toJson(obj));
            System.out.println(message);
            out.println(gson.toJson(message));

            String responseMsg = in.readLine();
            messageResponse = gson.fromJson(responseMsg, MessageResponse.class);
        } catch (IOException e) {
            messageResponse.setResponseType(ResponseType.ERROR_CLIENT);
            throw new RuntimeException(e);
        }
        return messageResponse;
    }
}
