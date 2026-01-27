package com.Spring.elitedemo1.Controller;

import com.Spring.elitedemo1.Model.Rating;
import com.Spring.elitedemo1.Services.RatingServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RatingContoller {

    @Autowired
    private RatingServices ratingService;

    // ✅ Create rating
    @PostMapping("/postRating")
    public ResponseEntity<Rating> addRating(
            @RequestBody Rating rating,
            HttpServletRequest request) {

        String userId = (String) request.getAttribute("userId");
        return ResponseEntity.ok(ratingService.addRating(rating, userId));
    }

    // ✅ Fetch ratings for table
    @GetMapping("/rating")
    public ResponseEntity<List<Rating>> getMyRatings(
            HttpServletRequest request) {

        String userId = (String) request.getAttribute("userId");
        return ResponseEntity.ok(ratingService.getMyRatings(userId));
    }

    // ✅ NEW: Get rating by ID
    @GetMapping("/rating/{ratingId}")
    public ResponseEntity<Rating> getRatingById(
            @PathVariable String ratingId,
            HttpServletRequest request) {

        String userId = (String) request.getAttribute("userId");
        return ResponseEntity.ok(
                ratingService.getRatingById(ratingId, userId));
    }

    // ❌ Delete rating
    @DeleteMapping("/deleteRating/{ratingId}")
    public ResponseEntity<String> deleteRating(
            @PathVariable String ratingId,
            HttpServletRequest request) {

        String userId = (String) request.getAttribute("userId");
        ratingService.deleteRating(ratingId, userId);
        return ResponseEntity.ok("Rating deleted successfully");
    }
}
