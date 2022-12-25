package com.example.client.helper;

import com.example.client.DTO.Message;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ParserMessageFactory<T> {
    public T parsePayloadOne(Class<?> myClass, Message message) {
        var type = TypeToken.getParameterized(myClass).getType();
        return new Gson().fromJson(message.getPayload(), type);
    }

    public List<T> parsePayload(Class<?> myClass, Message message) {
        var type = TypeToken.getParameterized(ArrayList.class, myClass).getType();
        return new Gson().fromJson(message.getPayload(), type);
    }


}
