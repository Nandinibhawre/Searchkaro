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
@Document(collection = "ratings")
public class Rating {

    @Id
    private String ratingId;

    private String category;
    private String shop;

    private int rating;      // ⭐ 1–5
    private boolean status;  // true = Positive, false = Negative

    private String userId;   // 🔐 from JWT
    @CreatedDate
    private LocalDateTime createdAt;

    // getters & setters
}
