package com.Spring.elitedemo1.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "dashboard")
public class Dashboard {

    @Id
    private String id;

    private String userId;
    private String category;
    private String location;
    private Double rating;

    // getters & setters
}
