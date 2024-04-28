package com.capstone.dangdang.controller;

import com.capstone.dangdang.dto.request.JoinRequest;
import com.capstone.dangdang.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/join")
    @Operation(summary = "유저 회원가입")
    public ResponseEntity<String> join(@Valid @RequestBody JoinRequest joinRequest, BindingResult bindingResult) {
        if (memberService.checkLoginIdDuplicate(joinRequest.getLoginId())) {
            bindingResult.addError(new FieldError("joinRequest", "loginId", "ID가 존재합니다."));
        }
        if (!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())) {
            bindingResult.addError(new FieldError("joinRequest", "passwordCheck", "비밀번호가 일치하지 않습니다."));
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }
        memberService.securityJoin(joinRequest);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> adminPage() {
        return ResponseEntity.ok("관리자페이지 화면");
    }
}
