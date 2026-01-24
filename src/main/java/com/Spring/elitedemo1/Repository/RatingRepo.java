package com.Spring.elitedemo1.Repository;
import com.Spring.elitedemo1.Model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RatingRepo extends MongoRepository<Rating, String> {

    List<Rating> findByUserId(String userId);
}