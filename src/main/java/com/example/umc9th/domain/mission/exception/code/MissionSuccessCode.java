package com.example.umc9th.domain.mission.exception.code;

import com.example.umc9th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {

    STORE_MISSIONS_FOUND(HttpStatus.OK,
            "MISSION200_1",
            "가게 미션 목록 조회에 성공했습니다."),

    MY_ONGOING_MISSIONS_FOUND(HttpStatus.OK,
            "MISSION200_2",
            "나의 진행중인 미션 목록 조회에 성공했습니다."),

    MISSION_COMPLETED(HttpStatus.OK,
            "MISSION200_3",
            "미션을 완료 상태로 변경했습니다.");
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}