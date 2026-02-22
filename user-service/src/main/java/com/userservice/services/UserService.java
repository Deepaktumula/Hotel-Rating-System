package com.userservice.services;

import com.userservice.entities.User;

import java.util.List;

public interface UserService {

    //    Create User
    User saveUser(User user);

    //    Get all user
    List<User> getAllUsers();

    //    Get single user of given UserId
    User getUser(String id);

//    TODO:Delete
//    TODO:Update

}
