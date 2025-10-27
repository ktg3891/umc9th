package com.example.umc9th.domain.mission.repository;

import com.example.umc9th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    @Query(
            value = "SELECT m " +
                    "FROM Mission m " +
                    "JOIN m.store s " +
                    "LEFT JOIN MemberMission mm ON mm.mission = m AND mm.member.id = :memberId " +
                    "WHERE s.location.id = :locationId " +
                    "AND m.deadline >= CURRENT_TIMESTAMP " +
                    "AND mm.id IS NULL " +
                    "ORDER BY m.deadline ASC "
    )
    Page<Mission> findAvailableInLocationExcludingJoined(
            @Param("memberId") Long memberId,
            @Param("locationId") Long locationId,
            Pageable pageable
    );
}
