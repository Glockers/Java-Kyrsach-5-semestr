package com.example.client.model;

import lombok.Data;

@Data
public class Allowance {
    Integer id;
    UserDetail user;
    Integer percent;
}