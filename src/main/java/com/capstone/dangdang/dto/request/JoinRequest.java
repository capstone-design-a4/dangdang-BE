package com.capstone.dangdang.dto.request;

import com.capstone.dangdang.entity.MemberEntity;
import com.capstone.dangdang.enums.Gender;
import com.capstone.dangdang.enums.Provider;
import com.capstone.dangdang.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {
    @NotBlank(message = "ID를 입력하세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
    private String passwordCheck;

    @NotBlank(message = "이름을 입력하세요.")
    private String name;

    @NotBlank(message = "이메일을 입력하세요.")
    private String email;

    private Gender gender;
    private String phoneNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birth;


    // 비밀번호 암호화 X
    public MemberEntity toEntity(){
        return MemberEntity.builder()
                .loginId(this.loginId)
                .password(this.password)
                .name(this.name)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .birth(this.birth)
                .gender(this.gender)
                .imageUrl(UUID.randomUUID().toString())
                .provider(Provider.KAKAO)
                .role(Role.USER)
                .build();
    }

}