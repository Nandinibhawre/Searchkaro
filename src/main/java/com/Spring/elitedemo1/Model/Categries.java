package com.Spring.elitedemo1.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "categories")
public class Categries {

    @Id
        private String categoryId;
    private String userId;      // From JWT
    private String role;        // Buyer / Seller
    private String categoryName;
    private String productName;
    private Boolean status;      // Positive / Negative

    @CreatedDate
    private LocalDateTime createdAt;
}