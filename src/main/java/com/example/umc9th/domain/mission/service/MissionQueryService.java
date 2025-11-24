package com.example.umc9th.domain.mission.service;

import com.example.umc9th.domain.mission.dto.MissionResDTO;

public interface MissionQueryService {

    MissionResDTO.MissionPreViewListDTO getStoreMissions(Long storeId, int page);

    MissionResDTO.MissionPreViewListDTO getMyOngoingMissions(Long memberId, int page);

    MissionResDTO.MissionPreViewDTO completeMission(Long memberId, Long memberMissionId);
}