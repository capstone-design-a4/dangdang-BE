package com.capstone.dangdang.entity;

import com.capstone.dangdang.enums.CafeName;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "cafe")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Data
@Entity
public class CafeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private CafeName name;

    @OneToMany(mappedBy = "cafe", fetch = FetchType.LAZY) @Builder.Default
    private List<DrinkEntity> drinkList = new ArrayList<>();
}