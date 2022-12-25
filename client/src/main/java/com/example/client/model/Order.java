package com.example.client.model;

import com.example.client.type.ProjectStatus;
import lombok.Data;

import java.sql.Date;

@Data
public class Order {
    Integer id;

    String projectName;
    String description;
    Date dateEnd;
    ProjectStatus projectStatus;

    CustomerContact customerContact;
}
