package com.capstone.dangdang.dto;
import com.capstone.dangdang.entity.BoardEntity;
import com.capstone.dangdang.enums.CafeName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class BoardRequest {
    private Long id;
    private Integer number;
    private String title;
    private String content;
    private String name;
    private CafeName category;
    private Long view;
    private Integer sub;
    private Integer comment;
    private java.sql.Timestamp createDate;
    private Timestamp updateDate;

    // dto To entity
    public BoardEntity toEntity() {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(this.id);
        boardEntity.setNumber(this.number);
        boardEntity.setTitle(this.title);
        boardEntity.setContent(this.content);
        boardEntity.setName(this.name);
        boardEntity.setCategory(this.category);
        boardEntity.setView(this.view);
        boardEntity.setSub(this.sub);
        boardEntity.setComment(this.comment);
        boardEntity.setCreateDate(this.createDate);
        boardEntity.setUpdateDate(this.updateDate);

        return boardEntity;
    }
}