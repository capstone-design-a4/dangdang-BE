package com.capstone.dangdang.dto.entity;

import com.capstone.dangdang.entity.BoardEntity;
import com.capstone.dangdang.entity.CommentEntity;
import com.capstone.dangdang.enums.CafeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {

    private Long id;
    private String title;
    private String content;
    private MemberDto author;
    private CafeName category;
    private Long view;
    private Integer likes;
    @Builder.Default
    private List<CommentDto> commentList = new ArrayList<>();
    private Timestamp updateDate;

    public BoardDto(BoardEntity board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.author = new MemberDto(board.getMember());
        this.category = board.getCategory();
        this.view = board.getView();
        this.likes = board.getLikeMemberIdList().size();
        for (CommentEntity commentEntity : board.getCommentList()) {
            this.commentList.add(new CommentDto(commentEntity));
        }
        this.updateDate = board.getUpdateDate();
    }
}
