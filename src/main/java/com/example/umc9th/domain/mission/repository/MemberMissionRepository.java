package com.example.umc9th.domain.mission.repository;

import com.example.umc9th.domain.member.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    // 진행 중인 미션만
    Page<MemberMission> findAllByMemberIdAndIsCompleteFalse(Long memberId, Pageable pageable);

    Optional<MemberMission> findByIdAndMemberId(Long id, Long memberId);
}

