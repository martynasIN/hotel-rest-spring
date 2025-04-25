package com.hotels.hotels.service;

import com.hotels.hotels.dao.ReviewRepository;
import com.hotels.hotels.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> findByHotelId(Long hotelId) {
        return reviewRepository.findByHotelId(hotelId);
    }

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }
}
