package com.capstone.dangdang.controller;

import com.capstone.dangdang.dto.JoinRequest;
import com.capstone.dangdang.dto.LoginRequest;
import com.capstone.dangdang.entity.MemberEntity;
import com.capstone.dangdang.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/dangdang")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<String> home(Authentication auth){
        if (auth != null){
            MemberEntity loginMember = memberService.getLoginMemberByLoginId(auth.getName());
            if (loginMember != null){
                return ResponseEntity.ok("Welcome, " + loginMember.getName() + "!");
            }
        }
        return ResponseEntity.ok("Welcome!");
    }

    @PostMapping("/join")
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

    @GetMapping("/login")
    public ResponseEntity<String> loginPage() {
        return ResponseEntity.ok("로그인 화면");
    }

    @GetMapping("/info")
    public ResponseEntity<String> memberInfo(Authentication auth) {
        MemberEntity loginMember = memberService.getLoginMemberByLoginId(auth.getName());
        if(loginMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 후 이용해주세요.");
        }
        return ResponseEntity.ok(loginMember.toString());
    }

    @GetMapping("/admin")
    public ResponseEntity<String> adminPage() {
        return ResponseEntity.ok("관리자페이지 화면");
    }
}