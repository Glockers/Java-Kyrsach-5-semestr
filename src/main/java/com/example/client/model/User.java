package com.example.client.model;

import com.example.client.type.RoleType;
import lombok.Data;

@Data
public class User {

    Integer id;
    String login;
    String password;
    RoleType roleType;


}
