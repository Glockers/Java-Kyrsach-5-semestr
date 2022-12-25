package org.example.DTO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import org.example.types.ResponseType;

import java.util.ArrayList;
import java.util.List;


@Data
public class MessageResponse extends Message{
    private ResponseType responseType;

}
