package com.example.umc9th.domain.mission.service;

import com.example.umc9th.domain.member.entity.mapping.MemberMission;
import com.example.umc9th.domain.mission.converter.MissionConverter;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.mission.dto.MissionResDTO;
import com.example.umc9th.domain.mission.exception.MissionException;
import com.example.umc9th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc9th.domain.mission.repository.MemberMissionRepository;
import com.example.umc9th.domain.mission.repository.MissionRepository;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.exception.StoreException;
import com.example.umc9th.domain.store.exception.code.StoreErrorCode;
import com.example.umc9th.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MissionQueryServiceImpl implements MissionQueryService {

    private final StoreRepository storeRepository;
    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;


    @Override
    public MissionResDTO.MissionPreViewListDTO getStoreMissions(Long storeId, int page) {

        if (page < 1) {

            throw new IllegalArgumentException("page는 1 이상이어야 합니다.");
        }

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(page - 1, 10);   // 한 페이지 10개
        Page<Mission> missions = missionRepository.findAllByStore(store, pageRequest);

        return MissionConverter.toMissionPreViewListDTO(missions);
    }

    @Override
    public MissionResDTO.MissionPreViewListDTO getMyOngoingMissions(Long memberId, int page) {

        if (page < 1) {
            throw new IllegalArgumentException("page는 1 이상이어야 합니다.");
        }

        PageRequest pageRequest = PageRequest.of(page - 1, 10);

        Page<MemberMission> memberMissions =
                memberMissionRepository.findAllByMemberIdAndIsCompleteFalse(memberId, pageRequest);

        return MissionConverter.toMyOngoingMissionListDTO(memberMissions);
    }

    @Override
    @Transactional
    public MissionResDTO.MissionPreViewDTO completeMission(Long memberId, Long memberMissionId) {

        // 1) 내가 가진 미션인지 확인
        MemberMission memberMission = memberMissionRepository
                .findByIdAndMemberId(memberMissionId, memberId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.NOT_OWNED));

        // 2) 이미 완료 상태인지 확인
        if (memberMission.isComplete()) {
            throw new MissionException(MissionErrorCode.ALREADY_COMPLETED);
        }

        // 3) 완료로 변경
        memberMission.complete();   // 엔티티 메서드

        // 4) 변경된 상태를 DTO로 반환
        return MissionConverter.toMyMissionDTO(memberMission);
    }
}