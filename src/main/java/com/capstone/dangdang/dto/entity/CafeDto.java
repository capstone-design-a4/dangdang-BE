package com.capstone.dangdang.dto.entity;

import com.capstone.dangdang.enums.CafeName;
import lombok.Data;

@Data
public class CafeDto {
    private Long id;
    private CafeName name;
}
