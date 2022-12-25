package org.example.model;

import lombok.Data;
import org.example.types.ProjectStatus;

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
