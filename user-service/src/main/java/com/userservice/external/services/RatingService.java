package com.userservice.external.services;

import com.userservice.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

    // Get all ratings
    @GetMapping("/ratings")
    ResponseEntity<List<Rating>> getRatings();

    // Get ratings by userId
    @GetMapping("/ratings/users/{userId}")
    ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable String userId);

    // Create rating
    @PostMapping("/ratings")
    ResponseEntity<Rating> createRating(@RequestBody Rating rating);

    // Update rating
    @PutMapping("/ratings/{ratingId}")
    ResponseEntity<Rating> updateRating(@PathVariable String ratingId, @RequestBody Rating rating);

    // Delete rating
    @DeleteMapping("/ratings/{ratingId}")
    ResponseEntity<Void> deleteRating(@PathVariable String ratingId);
}
