package com.example.umc9th.domain.mission.dto;

import com.example.umc9th.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MissionResDTO {

    @Getter
    @Builder
    public static class MissionPreViewDTO {
        private Long missionId;
        private String storeName;
        private String condition;   // 미션 내용
        private Integer point;      // 보상 포인트
        private LocalDate deadline; // 마감일
        private boolean ongoing;    // 내가 진행중인지 여부
    }

    @Getter
    @Builder
    public static class MissionPreViewListDTO {
        private List<MissionPreViewDTO> missions;
        private long totalCount;
        private boolean last;
    }
}
