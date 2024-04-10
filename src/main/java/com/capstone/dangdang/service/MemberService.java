package com.capstone.dangdang.service;

import com.capstone.dangdang.dto.JoinRequest;
import com.capstone.dangdang.dto.LoginRequest;
import com.capstone.dangdang.entity.MemberEntity;
import com.capstone.dangdang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean checkLoginIdDuplicate(String loginId){
        return memberRepository.existsByLoginId(loginId);
    }

    public void join(JoinRequest joinRequest) {
        memberRepository.save(joinRequest.toEntity());
    }

    public void securityJoin(JoinRequest joinRequest){
        if(memberRepository.existsByLoginId(joinRequest.getLoginId())){
            return;
        }

        joinRequest.setPassword(bCryptPasswordEncoder.encode(joinRequest.getPassword()));

        memberRepository.save(joinRequest.toEntity());
    }

    public MemberEntity login(LoginRequest loginRequest) {
        MemberEntity findMember = memberRepository.findByLoginId(loginRequest.getLoginId());

        if(findMember == null){
            return null;
        }

        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), findMember.getPassword())) {
            return null;
        }

        return findMember;
    }

    public MemberEntity getLoginMemberById(Long memberId){
        if(memberId == null) return null;

        Optional<MemberEntity> findMember = memberRepository.findById(memberId);
        return findMember.orElse(null);

    }

    public MemberEntity getLoginMemberByLoginId(String loginId){
        if(loginId == null) return null;

        return memberRepository.findByLoginId(loginId);

    }

}
