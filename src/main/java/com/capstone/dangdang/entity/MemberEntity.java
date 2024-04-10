package com.capstone.dangdang.entity;

import com.capstone.dangdang.enums.Gender;
import com.capstone.dangdang.enums.Provider;
import com.capstone.dangdang.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "member")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "login_id")
    String loginId;

    @Column(name = "password")
    String password;

    @Column(name = "email")
    String email;

    @Column(name = "name")
    String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    Provider provider;

    @Column(name = "social_id")
    String socialId;

    @Column(name = "image_url")
    String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    Role role;
}
