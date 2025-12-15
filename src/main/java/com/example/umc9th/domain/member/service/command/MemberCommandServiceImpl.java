package com.example.umc9th.domain.member.service.command;


import com.example.umc9th.domain.member.converter.MemberConverter;
import com.example.umc9th.domain.member.dto.MemberReqDto;
import com.example.umc9th.domain.member.dto.MemberResDto;
import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입

    @Override
    public MemberResDto.JoinDto signup(MemberReqDto.JoinDTO dto) {

        Member member = MemberConverter.toMember(dto);

        // 비밀번호 암호화 저장
        member.setPassword(passwordEncoder.encode(dto.password()));

        memberRepository.save(member);
        return MemberConverter.toJoinDTO(member);
    }
}
