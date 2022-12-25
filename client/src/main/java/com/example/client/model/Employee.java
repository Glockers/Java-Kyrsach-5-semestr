package com.example.client.model;

import lombok.Data;

import java.sql.Date;

@Data
public class Employee {
    UserDetail userDetail;
    Post post;
    Date hiring_date;
}

