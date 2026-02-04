package com.Spring.elitedemo1.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Data
@Document(collection = "table_user")
public class User {

    @Id
    private String Luserid;
    private String name;
    private String email;
    private String password;


}
