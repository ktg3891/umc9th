package com.example.umc9th.domain.member.repository;

import com.example.umc9th.domain.member.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    @EntityGraph(attributePaths = "mission")
    Page<MemberMission> findByMemberIdAndIsCompleteOrderByMission_ASC(
            Long missionId,
            boolean isComplete,
            Pageable pageable);

    long countByMemberIdAndIsCompleteTrueAndMission_Store_Location_Id(
            Long memberId,
            Long locationId
    );
}

