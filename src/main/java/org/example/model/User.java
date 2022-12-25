package org.example.model;

import lombok.Data;
import org.example.types.RoleType;


@Data
public class User {

    Integer id;
    String login;
    String password;
    RoleType roleType;


}
