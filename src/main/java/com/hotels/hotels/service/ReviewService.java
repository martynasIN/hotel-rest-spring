package com.hotels.hotels.service;

import com.hotels.hotels.entity.Review;

import java.util.List;

public interface ReviewService {
    List<Review> findAll();
    List<Review> findByHotelId(Long hotelId);
    Review save(Review review);
}
