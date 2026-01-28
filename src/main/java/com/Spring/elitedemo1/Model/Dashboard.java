package com.Spring.elitedemo1.Model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "activities")
public class Activity {

    @Id
    private String id;

    private String userId;
    private String category;
    private String location;
    private Double rating;

    // getters & setters
}
