package com.example.umc9th.domain.member.service.command;

import com.example.umc9th.domain.member.dto.MemberReqDto;
import com.example.umc9th.domain.member.dto.MemberResDto;

public interface MemberCommandService {
    // 회원가입
    MemberResDto.JoinDto signup(
            MemberReqDto.JoinDTO dto
    );
}
