package com.capstone.dangdang.dto.request;

import com.capstone.dangdang.enums.CafeName;
import lombok.Getter;

@Getter
public class BoardRequest {

    private String title;
    private String content;
    private CafeName category;
}
