package com.capstone.dangdang.dto.entity;

import com.capstone.dangdang.entity.CafeEntity;
import com.capstone.dangdang.entity.DrinkEntity;
import com.capstone.dangdang.entity.MemberEntity;
import com.capstone.dangdang.enums.CafeName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrinkDto {

    private Long id;
    private String name;
    private CafeName cafeName;
    private Integer sugar;
    private Integer calorie;
    private String imageUrl;
    private boolean isBookmarked;

    public DrinkDto(DrinkEntity drink) {
        this.id = drink.getId();
        this.name = drink.getName();
        this.cafeName = drink.getCafe().getName();
        this.sugar = drink.getSugar();
        this.calorie = drink.getCalorie();
        this.imageUrl = drink.getImageUrl();
    }
}
