package com.example.umc9th.domain.member.converter;

import com.example.umc9th.domain.member.dto.MemberReqDto;
import com.example.umc9th.domain.member.dto.MemberResDto;
import com.example.umc9th.domain.member.entity.Member;

public class MemberConverter {

    public static MemberResDto.JoinDto toJoinDTO(Member member) {
        return MemberResDto.JoinDto.builder()
                .memberId(member.getId())
                .createAt(member.getCreatedAt())
                .build();
    }

    public static Member toMember(MemberReqDto.JoinDTO dto) {
        return Member.builder()
                .name(dto.name())
                .birth(dto.birth())
                .email(dto.email())
                .password(dto.password())
                .role(dto.role())
                .address(dto.address())
                .detailAddress(dto.specAddress())
                .gender(dto.gender())
                .build();
    }

    public static MemberResDto.LoginDTO toLoginDTO(Member member, String accessToken) {
        return MemberResDto.LoginDTO.builder()
                .memberId(member.getId())
                .accessToken(accessToken)
                .build();
    }
}
