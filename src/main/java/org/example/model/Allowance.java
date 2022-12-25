package org.example.model;

import lombok.Data;

@Data
public class Allowance {
    Integer id;
    UserDetail user;
    Integer percent;
}
