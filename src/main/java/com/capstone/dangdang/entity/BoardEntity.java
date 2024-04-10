package com.capstone.dangdang.entity;

import com.capstone.dangdang.enums.CafeName;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;


@Table(name = "board")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "number")
    Integer number;

    @Column(name = "title")
    String title;

    @Column(name = "content", columnDefinition = "TEXT")
    String content;

    @Column(name = "name")
    String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    CafeName category;

    @Column(name = "view")
    Long view;

    @Column(name = "sub")
    Integer sub;

    // 댓글 수
    @Column(name = "comment")
    Integer comment;

    @CreationTimestamp
    @Column(name = "create_date")
    private Timestamp createDate;

    @LastModifiedDate
    @Column(name = "update_date")
    private Timestamp updateDate;
}
