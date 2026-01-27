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
@Document(collection = "locations")
public class Location {

    @Id
    private String id;

    private String userId; // 🔥 owner of this location
    private String role;
    private String location;
    private String region;
    private String popular;

    // getters & setters
}
