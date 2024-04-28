package com.capstone.dangdang.controller;

import com.capstone.dangdang.dto.entity.MemberDto;
import com.capstone.dangdang.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Tag(name = "Member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/info")
    @Operation(summary = "유저 자신의 정보 조회")
    public MemberDto getInfo(Authentication auth) {
        return memberService.getMyInfo(auth);
    }

    @GetMapping(value = "/image/{memberId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Operation(summary = "유저 이미지 조회")
    public Resource getImage(@PathVariable Long memberId) {
        return memberService.getUserImage(memberId);
    }

    @PostMapping("/image")
    @Operation(summary = "유저 이미지 등록, 변경")
    public MemberDto updateImage(@RequestParam MultipartFile file, Authentication authentication) {
        return memberService.updateUserImage(file, authentication);
    }
}