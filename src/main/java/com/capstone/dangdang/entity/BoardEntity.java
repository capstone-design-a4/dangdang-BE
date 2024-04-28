package com.capstone.dangdang.entity;

import com.capstone.dangdang.enums.CafeName;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CollectionType;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Table(name = "board")
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Builder
@Entity
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "title")
    String title;

    @Column(name = "content", columnDefinition = "LONGTEXT")
    String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private MemberEntity member;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    CafeName category;

    @Column(name = "view")
    Long view;

    @ElementCollection
    @Builder.Default
    private List<Long> likeMemberIdList = new ArrayList<>();

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<CommentEntity> commentList = new ArrayList<>();

    @LastModifiedDate
    @Column(name = "update_date")
    private Timestamp updateDate;

    public void addComment(CommentEntity commentEntity) {
        commentList.add(commentEntity);
    }
}
