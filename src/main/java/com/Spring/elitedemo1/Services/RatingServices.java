package com.Spring.elitedemo1.Services;

import com.Spring.elitedemo1.Model.Rating;
import com.Spring.elitedemo1.Repository.RatingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServices {

    @Autowired
    private RatingRepo ratingRepository;

    public Rating addRating(Rating rating, String userId) {
        rating.setUserId(userId);
        rating.setStatus(rating.getRating() >= 3);
        return ratingRepository.save(rating);
    }

    public List<Rating> getMyRatings(String userId) {
        return ratingRepository.findByUserId(userId);
    }

    // ✅ NEW: Get rating by ID (user-specific)
    public Rating getRatingById(String ratingId, String userId) {

        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found"));

        // 🔐 Security check
        if (!rating.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        return rating;
    }

    public void deleteRating(String ratingId, String userId) {

        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found"));

        if (!rating.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        ratingRepository.delete(rating);
    }
}
