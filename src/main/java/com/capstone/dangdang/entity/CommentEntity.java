package com.capstone.dangdang.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;

@Table(name = "comment")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borad_id")
    BoardEntity board;

    @Column(name = "name")
    String name;

    // 댓글 내용
    @Column(name = "content", columnDefinition = "TEXT")
    String content;

    @CreationTimestamp
    @Column(name = "create_date")
    private Timestamp createDate;

    @LastModifiedDate
    @Column(name = "update_date")
    private Timestamp updateDate;
}
