package com.example.umc9th.domain.review.repository;

import com.example.umc9th.domain.review.entity.QReview;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.store.entity.QLocation;
import com.example.umc9th.domain.store.entity.QStore;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;
import com.querydsl.core.types.Predicate;

@RequiredArgsConstructor
public class ReviewQueryDslImpl implements ReviewQueryDsl{

    private final ReviewRepository reviewRepository;
    private final EntityManager em;

    @Override
    public List<Review> searchReview(
            Predicate predicate
    ){

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QReview review = QReview.review;
        QStore store = QStore.store;
        QLocation location = QLocation.location;

        return queryFactory
                .selectFrom(review)
                .leftJoin(store).on(store.id.eq(review.store.id)).fetchJoin()
                .leftJoin(location).on(location.id.eq(store.location.id)).fetchJoin()
                .where(predicate)
                .fetch();
    }
}
