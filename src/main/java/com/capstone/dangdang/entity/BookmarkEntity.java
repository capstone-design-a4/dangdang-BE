package com.capstone.dangdang.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "bookmark")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class BookmarkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    DrinkEntity drink;

    @ManyToOne(fetch = FetchType.LAZY)
    CafeEntity cafe;
}
