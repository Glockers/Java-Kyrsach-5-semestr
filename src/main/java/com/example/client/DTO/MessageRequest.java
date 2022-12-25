package com.example.client.DTO;

import com.example.client.type.RequestType;
import lombok.Data;

@Data
public class MessageRequest extends Message{
    private RequestType requestType;

}

