package com.example.client.model;

import lombok.Data;

import java.sql.Date;

@Data
public class Aptitude {
    Integer id;

    Post post;

    String name_test;

    Integer size_allowance;

    Date date;
}
