package com.capstone.dangdang.service;

import com.capstone.dangdang.dto.entity.MemberDto;
import com.capstone.dangdang.dto.entity.RecordDto;
import com.capstone.dangdang.dto.request.JoinRequest;
import com.capstone.dangdang.dto.request.LoginRequest;
import com.capstone.dangdang.entity.MemberEntity;
import com.capstone.dangdang.entity.RecordEntity;
import com.capstone.dangdang.error.CustomException;
import com.capstone.dangdang.error.ErrorCode;
import com.capstone.dangdang.repository.MemberRepository;
import com.capstone.dangdang.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RecordRepository recordRepository;
    @Value("${file:}")
    private String rootFilePath;

    public boolean checkLoginIdDuplicate(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }

    @Transactional(readOnly = true)
    public MemberDto getMyInfo(Authentication authentication) {
        MemberEntity memberEntity = memberRepository.findByLoginId(authentication.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        return new MemberDto(memberEntity);
    }

    public Resource getUserImage(Long memberId) {
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        try {
            Path path = Paths.get(rootFilePath, memberEntity.getImageUrl()).toAbsolutePath();
            Resource imageResource = new UrlResource(path.toUri());

            if (imageResource.exists()) {
                return imageResource;
            } else {
                throw new CustomException(ErrorCode.FILE_NOT_FOUND);
            }

        } catch (IOException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public MemberDto updateUserImage(MultipartFile file, Authentication authentication) {
        MemberEntity memberEntity = memberRepository.findByLoginId(authentication.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        try {
            Path path = Paths.get(rootFilePath, memberEntity.getImageUrl()).toAbsolutePath();
            if (StringUtils.hasText(memberEntity.getImageUrl())) {
                Files.deleteIfExists(path);
            }

            Path newPath = Paths.get(rootFilePath, memberEntity.getImageUrl()).toAbsolutePath();
            Files.createDirectories(newPath.getParent());
            file.transferTo(newPath.toFile());

        } catch (IOException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        return new MemberDto(memberEntity);
    }

    public void join(JoinRequest joinRequest) {
        memberRepository.save(joinRequest.toEntity());
    }

    public void securityJoin(JoinRequest joinRequest) {
        if (memberRepository.existsByLoginId(joinRequest.getLoginId())) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_LOGIN_ID);
        }

        joinRequest.setPassword(bCryptPasswordEncoder.encode(joinRequest.getPassword()));
        MemberEntity memberEntity = joinRequest.toEntity();
        memberRepository.save(memberEntity);

        RecordEntity recordEntity = RecordEntity.builder()
                .count(0)
                .drinkList(new ArrayList<>())
                .member(memberEntity)
                .goal(0)
                .build();

        memberEntity.setRecord(recordEntity);

        recordRepository.save(recordEntity);
    }

//    public MemberEntity login(LoginRequest loginRequest) {
//        MemberEntity findMember = memberRepository.findByLoginId(loginRequest.getLoginId());
//
//        if (findMember == null) {
//            return null;
//        }
//
//        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), findMember.getPassword())) {
//            return null;
//        }
//
//        return findMember;
//    }
//
//    public MemberEntity getLoginMemberById(Long memberId) {
//        if (memberId == null) return null;
//
//        Optional<MemberEntity> findMember = memberRepository.findById(memberId);
//        return findMember.orElse(null);
//
//    }
//
//    public MemberEntity getLoginMemberByLoginId(String loginId) {
//        if (loginId == null) return null;
//
//        return memberRepository.findByLoginId(loginId);
//
//    }

}
