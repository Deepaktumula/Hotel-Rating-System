package com.ratingservice.service;

import com.ratingservice.entities.Rating;

import java.util.List;

public interface RatingService {

    //    Create
    Rating createRating(Rating rating);

    //    Get All Ratings
    List<Rating> getAllRatings();

    //    Get Ratings by User ID
    List<Rating> getRatingsByUserId(String userId);

    //    Get All by Hotel
    List<Rating> getRatingsByHotelId(String hotelId);
}
