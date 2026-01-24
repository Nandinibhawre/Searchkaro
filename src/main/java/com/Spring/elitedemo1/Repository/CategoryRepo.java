package com.Spring.elitedemo1.Repository;


import com.Spring.elitedemo1.Model.Categries;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends MongoRepository<Categries, String> {

    List<Categries> findByUserId(String userId);
    Optional<Categries> findByCategoryIdAndUserId(String categoryId, String userId);
}