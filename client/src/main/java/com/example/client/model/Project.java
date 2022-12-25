package com.example.client.model;

import lombok.Data;

import java.sql.Date;

@Data
public class Project {
    Integer id;
    Order order;

    Date dateStart;
    Date factDate;
};
