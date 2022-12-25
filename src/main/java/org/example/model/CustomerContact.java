package org.example.model;

import lombok.Data;

@Data
public class CustomerContact {
    User user;
    String fio;
    String email;
}
