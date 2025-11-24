package com.example.umc9th.domain.mission.controller;

import com.example.umc9th.domain.mission.dto.MissionResDTO;
import com.example.umc9th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc9th.domain.mission.service.MissionQueryService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missions")
public class MissionController implements MissionControllerDocs {

    private final MissionQueryService missionQueryService;

    @Override
    @GetMapping("/store/{storeId}")
    public ApiResponse<MissionResDTO.MissionPreViewListDTO> getStoreMissions(
            @PathVariable Long storeId,
            @RequestParam Integer page
    ) {
        if (page == null || page < 1) {
            throw new IllegalArgumentException("page는 1 이상이어야 합니다.");
        }

        return ApiResponse.onSuccess(
                MissionSuccessCode.STORE_MISSIONS_FOUND,
                missionQueryService.getStoreMissions(storeId, page)
        );
    }

    @Override
    @GetMapping("/me")
    public ApiResponse<MissionResDTO.MissionPreViewListDTO> getMyOngoingMissions(
            @RequestParam Long memberId,
            @RequestParam Integer page
    ) {
        if (page == null || page < 1) {
            throw new IllegalArgumentException("page는 1 이상이어야 합니다.");
        }

        return ApiResponse.onSuccess(
                MissionSuccessCode.MY_ONGOING_MISSIONS_FOUND,
                missionQueryService.getMyOngoingMissions(memberId, page)
        );
    }

    @PatchMapping("/me/{memberMissionId}/complete")
    public ApiResponse<MissionResDTO.MissionPreViewDTO> completeMission(
            @RequestParam Long memberId,
            @PathVariable Long memberMissionId
    ) {
        return ApiResponse.onSuccess(
                MissionSuccessCode.MISSION_COMPLETED,
                missionQueryService.completeMission(memberId, memberMissionId)
        );
    }
}