package com.capstone.dangdang.dto.request;

import com.capstone.dangdang.enums.CafeName;
import lombok.Data;

@Data
public class BoardUpdateRequest {

    private Long boardId;
    private String title;
    private String content;
}
