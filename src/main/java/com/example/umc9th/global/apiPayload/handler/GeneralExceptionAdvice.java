package com.example.umc9th.global.apiPayload.handler;

import com.example.umc9th.domain.mission.exception.MissionException;
import com.example.umc9th.domain.review.exception.ReviewException;
import com.example.umc9th.domain.review.exception.code.ReviewErrorCode;
import com.example.umc9th.domain.store.exception.StoreException;
import com.example.umc9th.domain.store.exception.code.StoreErrorCode;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.BaseErrorCode;
import com.example.umc9th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc9th.global.apiPayload.exception.GeneralException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionAdvice {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Void>> handleException(GeneralException ex) {

        return ResponseEntity.status(ex.getCode().getHttpStatus())
                .body(ApiResponse.onFailure(
                        ex.getCode(),
                        null)
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(
            Exception ex
    ) {

        BaseErrorCode code = GeneralErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ApiResponse.onFailure(
                                code,
                                ex.getMessage()
                        )
                );
    }

    @ExceptionHandler(ReviewException.class)
    public ResponseEntity<ApiResponse<Void>> handleReviewException(ReviewException ex) {

        ReviewErrorCode errorCode = ex.getCode();

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ApiResponse.onFailure(
                        errorCode,
                        null
                ));
    }

    @ExceptionHandler(StoreException.class)
    public ResponseEntity<ApiResponse<Void>> handleStoreException(StoreException ex) {

        StoreErrorCode errorCode = ex.getErrorCode();

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ApiResponse.onFailure(
                        errorCode,
                        null
                ));
    }

    @ExceptionHandler(MissionException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissionException(MissionException ex) {

        BaseErrorCode code = ex.getCode();

        return ResponseEntity.status(code.getHttpStatus())
                .body(ApiResponse.onFailure(
                        code,
                        null
                ));
    }

}
