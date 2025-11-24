package com.example.umc9th.domain.mission.controller;

import com.example.umc9th.domain.mission.dto.MissionResDTO;
import com.example.umc9th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface MissionControllerDocs {
    @Operation(
            summary = "특정 가게의 미션 목록 조회",
            description = "storeId를 기준으로 해당 가게의 미션들을 페이지네이션으로 조회합니다. 한 페이지당 10개."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "가게를 찾을 수 없음")
    })
    ApiResponse<MissionResDTO.MissionPreViewListDTO> getStoreMissions(
            @Parameter(description = "가게 ID") @PathVariable Long storeId,
            @Parameter(description = "1 이상의 페이지 번호") @RequestParam Integer page
    );

    @Operation(
            summary = "내가 진행중인 미션 목록 조회",
            description = "memberId 기준으로 현재 진행중인 미션들만 페이지네이션으로 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공")
    })
    ApiResponse<MissionResDTO.MissionPreViewListDTO> getMyOngoingMissions(
            @Parameter(description = "회원 ID (임시)") @RequestParam Long memberId,
            @Parameter(description = "1 이상의 페이지 번호") @RequestParam Integer page
    );
}
