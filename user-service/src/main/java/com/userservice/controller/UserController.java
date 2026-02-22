package com.userservice.controller;

import com.userservice.entities.User;
import com.userservice.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    //    Create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    int retryCount = 1;

    //    Single user get
    @GetMapping("/{userId}")
//    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        log.info("Retry Count :: {}", retryCount++);
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    //    All user get
    @GetMapping()
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallbackAll")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }


    //    Creating ratingHotelFallback method for CircuitBreaker
    public ResponseEntity<User> ratingHotelFallback(@PathVariable String userId, Exception e) {
        log.info(" Fallback is executed because the service is down. {}", e.getMessage());
        User user = User.builder().email("dummy@gmail.com").name("Dummy Name").about("This user is created dummy because some service is down.").userId("111").build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(user);
    }

    // Fallback for getAllUser()
    public ResponseEntity<List<User>> ratingHotelFallbackAll(Exception e) {
        User user = User.builder()
                .userId("111")
                .name("Dummy Name")
                .email("dummy@gmail.com")
                .about("Fallback all users")
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(List.of(user));
    }

}
