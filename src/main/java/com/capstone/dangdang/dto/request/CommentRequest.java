package com.capstone.dangdang.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRequest {

    private Long boardId;
    @NotBlank(message = "댓글을 입력하세요")
    private String comment;
}
