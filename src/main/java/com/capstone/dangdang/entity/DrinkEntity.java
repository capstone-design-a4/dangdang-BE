package com.capstone.dangdang.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "drink")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DrinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "name")
    String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    CafeEntity cafe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    MemberEntity member;

    @Column(name = "sugar")
    Integer sugar;

    @Column(name = "calorie")
    Integer calorie;

    @Column(name = "image_url")
    String imageUrl;
}
