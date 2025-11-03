package com.example.umc9th.domain.review.service;

import com.example.umc9th.domain.review.entity.Review;
import java.util.List;

public interface ReviewService {

    List<Review> searchReview(Integer memberId, String query, String type);
}