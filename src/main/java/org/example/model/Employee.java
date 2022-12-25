package org.example.model;

import lombok.Data;

import java.sql.Date;
@Data
public class Employee {
    UserDetail userDetail;
    Post post;
    Date hiring_date;
}
