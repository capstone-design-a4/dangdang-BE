package com.capstone.dangdang.dto.entity;

import com.capstone.dangdang.entity.CommentEntity;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommentDto {

    private Long id;
    private Long memberId;
    private Long boardId;
    private String name;
    private String content;
    private Timestamp updateDate;

    public CommentDto(CommentEntity commentEntity) {
        this.id = commentEntity.getId();
        this.memberId = commentEntity.getMember().getId();
        this.boardId = commentEntity.getBoard().getId();
        this.name = commentEntity.getMember().getName();
        this.content = commentEntity.getContent();
        this.updateDate = commentEntity.getUpdateDate();
    }
}
