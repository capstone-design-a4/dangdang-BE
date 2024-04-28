package com.capstone.dangdang.dto.entity;

import com.capstone.dangdang.entity.DrinkEntity;
import com.capstone.dangdang.entity.DrinkRecordEntity;
import com.capstone.dangdang.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
public class DrinkRecordDto {

    private Long id;
    private DrinkDto drink;
    private Long memberId;
    private Timestamp createDate;
    public DrinkRecordDto(DrinkRecordEntity drinkRecord) {
        this.id = drinkRecord.getId();
        this.drink = new DrinkDto(drinkRecord.getDrink());
        this.memberId = drinkRecord.getMember().getId();
        this.createDate = drinkRecord.getCreateDate();
    }

}
