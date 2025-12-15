package com.example.umc9th.domain.review.service.query;

import com.example.umc9th.domain.review.dto.ReviewResDTO;
import com.example.umc9th.domain.review.entity.Review;

import java.util.List;

public interface ReviewQueryService {

    // filter/type 기반 검색 (간단 버전)
    List<Review> searchReview(String filter, String type);

    // 가게 이름으로 리뷰 미리보기 리스트 조회(페이징)
    ReviewResDTO.ReviewPreViewListDTO findReview(String storeName, Integer page);
}