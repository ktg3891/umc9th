package com.example.umc9th.domain.review.service.query;

import com.example.umc9th.domain.review.converter.ReviewConverter;
import com.example.umc9th.domain.review.dto.ReviewResDTO;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.exception.StoreException;
import com.example.umc9th.domain.store.exception.code.StoreErrorCode;
import com.example.umc9th.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;

    /**
     * 리뷰 검색 (현재 ReviewRepository가 페이징 store조회만 있으므로,
     * - type=STORE 일 때: storeName=filter 로 보고 첫 페이지 20개 반환
     * - 그 외: 전체 리뷰 반환 (JpaRepository 기본 메서드 findAll 사용)
     */
    @Override
    public List<Review> searchReview(String filter, String type) {

        if ("STORE".equalsIgnoreCase(type)) {
            Store store = storeRepository.findByName(filter)
                    .orElseThrow(() -> new StoreException(StoreErrorCode.NOT_FOUND));

            Pageable pageable = PageRequest.of(0, 20);
            Page<Review> pageResult = reviewRepository.findAllByStore(store, pageable);
            return pageResult.getContent();
        }

        // fallback: 전체 리뷰
        return reviewRepository.findAll();
    }

    /**
     * 가게별 리뷰 조회 (페이징) -> DTO 변환
     */
    @Override
    public ReviewResDTO.ReviewPreViewListDTO findReview(String storeName, Integer page) {

        Store store = storeRepository.findByName(storeName)
                .orElseThrow(() -> new StoreException(StoreErrorCode.NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(page, 5);
        Page<Review> result = reviewRepository.findAllByStore(store, pageRequest);

        return ReviewConverter.toReviewPreviewListDTO(result);
    }
}
