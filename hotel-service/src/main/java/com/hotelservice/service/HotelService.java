package com.hotelservice.service;

import com.hotelservice.entities.Hotel;

import java.util.List;

public interface HotelService {

    //     Create
    Hotel create(Hotel hotel);

    //    Get all
    List<Hotel> getAllHotels();

    //    get Single
    Hotel getHotelById(String id);
}
