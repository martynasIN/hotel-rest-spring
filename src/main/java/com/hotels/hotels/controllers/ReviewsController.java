package com.hotels.hotels.controllers;

import com.hotels.hotels.dto.ReviewCreateDTO;
import com.hotels.hotels.dto.ReviewResponseDTO;
import com.hotels.hotels.entity.Hotel;
import com.hotels.hotels.entity.Review;
import com.hotels.hotels.mapper.ReviewMapper;
import com.hotels.hotels.service.HotelsService;
import com.hotels.hotels.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewsController {

    private final ReviewService reviewService;
    private final HotelsService hotelsService;

    @Autowired
    public ReviewsController(ReviewService reviewService, HotelsService hotelsService) {
        this.reviewService = reviewService;
        this.hotelsService = hotelsService;
    }

    // GET /api/reviews
    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviews() {
        List<Review> reviews = reviewService.findAll();
        List<ReviewResponseDTO> response = reviews.stream()
                .map(ReviewMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // GET /api/hotels/{hotelId}/reviews
    @GetMapping("/hotels/{hotelId}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByHotelId(@PathVariable Long hotelId) {
        List<Review> reviews = reviewService.findByHotelId(hotelId);
        List<ReviewResponseDTO> response = reviews.stream()
                .map(ReviewMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // POST /api/reviews
    @PostMapping("/reviews")
    public ResponseEntity<ReviewResponseDTO> addReview(@Valid @RequestBody ReviewCreateDTO reviewDTO) {
        Hotel hotel = hotelsService.findById(reviewDTO.getHotelId().intValue());
        Review review = ReviewMapper.toEntity(reviewDTO, hotel);
        Review saved = reviewService.save(review);
        return ResponseEntity.ok(ReviewMapper.toResponse(saved));
    }
}
