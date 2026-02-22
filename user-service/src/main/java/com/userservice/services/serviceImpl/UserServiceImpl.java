package com.userservice.services.serviceImpl;

import com.userservice.entities.Hotel;
import com.userservice.entities.Rating;
import com.userservice.entities.User;
import com.userservice.exceptions.ResourceNotFoundException;
import com.userservice.external.services.HotelService;
import com.userservice.external.services.RatingService;
import com.userservice.repositories.UserRepository;
import com.userservice.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RatingService ratingService;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();

        users.parallelStream().forEach(user -> {
            ResponseEntity<List<Rating>> ratingsResponse = ratingService.getRatingsByUserId(user.getUserId());
            List<Rating> ratings = ratingsResponse.getBody();

            if (ratings != null && !ratings.isEmpty()) {
                ratings.parallelStream().forEach(rating -> {
                    Hotel hotel = hotelService.getHotel(rating.getHotelId());
                    rating.setHotel(hotel);
                });
                user.setRatings(ratings);
            }
        });

        return users;
    }

    @Override
    public User getUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with Id not found"));
        // Fetch ratings via Feign and extract body
        ResponseEntity<List<Rating>> ratingsResponse = ratingService.getRatingsByUserId(user.getUserId());
        List<Rating> ratings = ratingsResponse.getBody();

        if (ratings != null && !ratings.isEmpty()) {
            ratings.parallelStream().forEach(rating -> {
                Hotel hotel = hotelService.getHotel(rating.getHotelId());
                rating.setHotel(hotel);
            });
            user.setRatings(ratings);
        }

        return user;
    }


//    @Override
//    public List<User> getAllUsers() {
//        List<User> users = userRepository.findAll();
//
//        users.parallelStream().forEach(user -> {
//            Rating[] ratingsArray = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);
//            logger.info("{} ", (Object) ratingsArray);
//            if (ratingsArray != null) {
//                List<Rating> ratingList = Arrays.stream(ratingsArray).parallel().map(rating -> {
////                  fetch hotel for each rating -- Using RestTemplate
////                    Hotel hotel = restTemplate.getForObject("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
//
////                    Using FeignClient
//                    Hotel hotel = hotelService.getHotel(rating.getHotelId());
//                    rating.setHotel(hotel);
//

    /// /                    Returning the rating
//                    return rating;
//                }).toList();
//                user.setRatings(ratingList);
//            }
//        });
//        return users;
//    }


//    @Override
//    public User getUser(String id) {
////        Get user from database with the help of userRepository
//        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with Id not found"));
//
//        // Fetch ratings of this user from rating service
//        Rating[] ratingsArray = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);
//
//        if (ratingsArray != null) {
//            List<Rating> ratings = Arrays.stream(ratingsArray).parallel().map(rating -> {
//
////                Hotel hotel = restTemplate.getForObject("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
//
//                Hotel hotel = hotelService.getHotel(rating.getHotelId());
//                rating.setHotel(hotel);
//
////                Returning the Rating
//                return rating;
//            }).toList();
//
//            user.setRatings(ratings);
//        }
//        return user;
//    }

}
