package com.capstone.dangdang.entity;

import com.capstone.dangdang.enums.CafeName;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "cafe")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    CafeName name;

    @Column(name = "image_url")
    String imageUrl;
}
