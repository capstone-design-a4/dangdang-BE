package com.capstone.dangdang.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import java.sql.Timestamp;

@Table(name = "record")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    MemberEntity member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drink_id")
    DrinkEntity drink;

    @Column(name = "count")
    Integer count;

    @Column(name = "d_suger")
    Integer D_Sugar;

    @Column(name = "d_calorie")
    Integer D_Calorie;

    @Column(name = "w_suger")
    Integer W_Suger;

    @Column(name = "w_calorie")
    Integer W_Calorie;

    @Column(name = "m_suger")
    Integer M_Suger;

    @Column(name = "m_calorie")
    Integer M_Calorie;

    @Column(name = "goal")
    Integer Goal;

    @CreationTimestamp
    @Column(name = "create_date")
    private Timestamp createDate;
}
