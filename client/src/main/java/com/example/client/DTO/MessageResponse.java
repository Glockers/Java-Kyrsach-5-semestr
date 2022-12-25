package com.example.client.DTO;

import com.example.client.type.ResponseType;

import lombok.Data;


@Data
public class MessageResponse extends Message{
    private ResponseType responseType;
}
