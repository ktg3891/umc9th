package com.example.umc9th.domain.mission.exception.code;

import com.example.umc9th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND,
            "MISSION404_1",
            "해당 미션을 찾을 수 없습니다."),

    INVALID_PAGE(HttpStatus.BAD_REQUEST,
            "MISSION400_1",
            "page는 1 이상의 숫자여야 합니다."),

    ALREADY_COMPLETED(HttpStatus.BAD_REQUEST,
            "MISSION400_2",
            "이미 완료된 미션입니다."),

    NOT_OWNED(HttpStatus.FORBIDDEN,
            "MISSION403_1",
            "해당 미션에 대한 권한이 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}