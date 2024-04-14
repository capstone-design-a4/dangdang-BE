package com.capstone.dangdang.dto;

import com.capstone.dangdang.entity.BoardEntity;
import com.capstone.dangdang.enums.CafeName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardCreationRequest {
    private String title;
    private String content;
    private CafeName category;

    public BoardEntity toEntity() {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setTitle(this.title);
        boardEntity.setContent(this.content);
        boardEntity.setCategory(this.category);
        return boardEntity;
    }
}