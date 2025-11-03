package com.example.umc9th.domain.review.service;

import com.example.umc9th.domain.review.entity.QReview;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import com.example.umc9th.domain.store.entity.QLocation;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public List<Review> searchReview(Integer memberId, String query, String type) {

        QReview review = QReview.review;
        QLocation location = QLocation.location;
        BooleanBuilder builder = new BooleanBuilder();

        if (type.equals("location")) {
            builder.and(review.store.name.containsIgnoreCase(query));
        }

        if (type.equals("star")) {
            float[] range = parseRange(query);       // [min, max) 형태
            if (range != null) {
                builder.and(review.star.goe(range[0]));
                builder.and(review.star.lt(range[1]));
            }
        }

        if (type.equals("both")) {
            String[] parts = query.split("&", 2);
            String storeName = parts.length > 0 ? parts[0].trim() : "";
            String starPart  = parts.length > 1 ? parts[1].trim() : "";

            builder.and(review.store.name.containsIgnoreCase(storeName));

            float[] range = parseRange(starPart);
            if (range != null) {
                builder.and(review.star.goe(range[0]));
                builder.and(review.star.lt(range[1]));
            }
        }

        return reviewRepository.searchReview(builder);
    }

    private float[] parseRange(String query) {
        float min, max;

        if (query.contains("~") || query.contains("-")) {
            String[] parts = query.split("[~-]");
            min = Float.parseFloat(parts[0].trim());
            max = Float.parseFloat(parts[1].trim());
            return new float[]{min, max};
        }
        if (query.matches("\\d+")) {
            float base = Float.parseFloat(query);
            return new float[]{base, base + 1f};
        }
        float value = Float.parseFloat(query);
        return new float[]{value, value + 0.1f};
    }
}
