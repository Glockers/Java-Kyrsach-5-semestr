package org.example.model;

import lombok.Data;

import java.sql.Date;

@Data
public class Chart {
    Date date;
    Integer count;
}
