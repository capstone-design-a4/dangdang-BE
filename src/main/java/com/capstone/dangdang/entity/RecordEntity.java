package com.capstone.dangdang.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Table(name = "record")
@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    MemberEntity member;

    @OneToMany(fetch = FetchType.LAZY) @Builder.Default
    List<DrinkRecordEntity> drinkList = new ArrayList<>();

    @Column(name = "count")
    Integer count;

    @Column(name = "goal")
    Integer goal;

    @CreationTimestamp
    @Column(name = "create_date")
    private Timestamp createDate;

}