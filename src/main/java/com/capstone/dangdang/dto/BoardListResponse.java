package com.capstone.dangdang.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardListResponse {
    private Long id;
    private String title;
    private String name;
    private Long view;
    private Integer sub;
}