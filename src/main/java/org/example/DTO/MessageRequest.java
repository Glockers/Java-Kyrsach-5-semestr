package org.example.DTO;


import lombok.Data;
import org.example.types.RequestType;

@Data
public class MessageRequest extends Message{
    private RequestType requestType;



}

