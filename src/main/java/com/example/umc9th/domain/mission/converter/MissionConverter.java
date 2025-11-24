package com.example.umc9th.domain.mission.converter;

import com.example.umc9th.domain.member.entity.mapping.MemberMission;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.mission.dto.MissionResDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public class MissionConverter {
    // 특정 가게 미션 DTO
    public static MissionResDTO.MissionPreViewDTO toMissionPreViewDTO(Mission mission) {

        return MissionResDTO.MissionPreViewDTO.builder()
                .missionId(mission.getId())
                .storeName(mission.getStore().getName())
                .condition(mission.getCondition())
                .point(mission.getPoint())
                .deadline(mission.getDeadline())
                .ongoing(false)
                .build();
    }

    public static MissionResDTO.MissionPreViewListDTO toMissionPreViewListDTO(
            Page<Mission> page
    ) {
        List<MissionResDTO.MissionPreViewDTO> list =
                page.stream()
                        .map(MissionConverter::toMissionPreViewDTO)
                        .toList();

        return MissionResDTO.MissionPreViewListDTO.builder()
                .missions(list)
                .totalCount(page.getTotalElements())
                .last(page.isLast())
                .build();
    }

    // 내가 진행중인 미션 변환
    public static MissionResDTO.MissionPreViewDTO toMyOngoingMissionDTO(MemberMission mm) {

        Mission mission = mm.getMission();

        return MissionResDTO.MissionPreViewDTO.builder()
                .missionId(mission.getId())
                .storeName(mission.getStore().getName())
                .condition(mission.getCondition())
                .point(mission.getPoint())
                .deadline(mission.getDeadline())
                .ongoing(true)
                .build();
    }

    public static MissionResDTO.MissionPreViewDTO toMyMissionDTO(MemberMission mm) {

        Mission mission = mm.getMission();

        return MissionResDTO.MissionPreViewDTO.builder()
                .missionId(mission.getId())
                .storeName(mission.getStore().getName())
                .condition(mission.getCondition())
                .point(mission.getPoint())
                .deadline(mission.getDeadline())
                .ongoing(!mm.isComplete())  // 진행중이면 true, 완료면 false
                .build();
    }



    public static MissionResDTO.MissionPreViewListDTO toMyOngoingMissionListDTO(
            Page<MemberMission> page
    ) {

        List<MissionResDTO.MissionPreViewDTO> list =
                page.stream()
                        .map(MissionConverter::toMyOngoingMissionDTO)
                        .toList();

        return MissionResDTO.MissionPreViewListDTO.builder()
                .missions(list)
                .totalCount(page.getTotalElements())
                .last(page.isLast())
                .build();
    }
}
