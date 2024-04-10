package com.capstone.dangdang.controller;

import com.capstone.dangdang.dto.JoinRequest;
import com.capstone.dangdang.dto.LoginRequest;
import com.capstone.dangdang.entity.MemberEntity;
import com.capstone.dangdang.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/dangdang")
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @GetMapping(value = {"", "/"})
    public String home(Model model, Authentication auth){
        model.addAttribute("pageName", "홈 화면");
        model.addAttribute("homePageName", "dangdang");

        if (auth != null){
            MemberEntity loginMember = memberService.getLoginMemberByLoginId(auth.getName());
            if (loginMember != null){
                model.addAttribute("name", loginMember.getName());
            }
        }

        return "home";
    }

    @GetMapping("/join")
    public String joinPage(Model model) {

        model.addAttribute("homePageName", "dangdang");
        model.addAttribute("pageName", "회원가입 화면");

        // 회원가입을 위해서 model 통해서 joinRequest 전달
        model.addAttribute("joinRequest", new JoinRequest());
        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute JoinRequest joinRequest,
                       BindingResult bindingResult, Model model) {

        model.addAttribute("homePageName", "dangdang");
        model.addAttribute("pageName", "회원가입 화면");

        // ID 중복 여부 확인
        if (memberService.checkLoginIdDuplicate(joinRequest.getLoginId())) {
            bindingResult.addError(new FieldError("joinRequest", "loginId", "ID가 존재합니다."));
        }

        // 비밀번호 = 비밀번호 체크 여부 확인
        if (!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())) {
            bindingResult.addError(new FieldError("joinRequest", "passwordCheck", "비밀번호가 일치하지 않습니다."));
        }

        // 에러가 존재할 시 다시 join.html로 전송
        if (bindingResult.hasErrors()) {
            return "join";
        }

        // 에러가 존재하지 않을 시 joinRequest 통해서 회원가입 완료
        memberService.securityJoin(joinRequest);

        // 회원가입 시 홈 화면으로 이동
        return "redirect:/dangdang";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {

        model.addAttribute("homePageName", "dangdang");
        model.addAttribute("pageName", "로그인 화면");

        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @GetMapping("/info")
    public String memberInfo(Authentication auth, Model model) {
        model.addAttribute("homePageName", "dangdang");
        model.addAttribute("pageName", "마이페이지 화면");

        MemberEntity loginMember = memberService.getLoginMemberByLoginId(auth.getName());

        if(loginMember == null) {
            return "redirect:/dangdang/login";
        }

        model.addAttribute("member", loginMember);
        return "info";
    }
    @GetMapping("/admin")
    public String adminPage(Model model) {

        model.addAttribute("homePageName", "dangdang");
        model.addAttribute("pageName", "관리자페이지 화면");

        return "admin";
    }
}
