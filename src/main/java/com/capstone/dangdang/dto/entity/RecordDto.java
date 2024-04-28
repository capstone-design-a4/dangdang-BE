package com.capstone.dangdang.dto.entity;

import com.capstone.dangdang.dto.response.DayStatDto;
import com.capstone.dangdang.dto.response.MonthStatDto;
import com.capstone.dangdang.dto.response.WeekStatDto;
import com.capstone.dangdang.entity.DrinkEntity;
import com.capstone.dangdang.entity.DrinkRecordEntity;
import com.capstone.dangdang.entity.MemberEntity;
import com.capstone.dangdang.entity.RecordEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordDto {

    private Long id;
    @Builder.Default
    private List<DrinkRecordDto> drinkList = new ArrayList<>();
    private Integer goal;
    private DayStatDto dayStat;
    private WeekStatDto weekStat;
    private MonthStatDto monthStat;


    public RecordDto(RecordEntity record) {
        this.id = record.getId();
        this.drinkList = new ArrayList<>();
        for (DrinkRecordEntity drinkRecordEntity : record.getDrinkList()) {
            drinkList.add(new DrinkRecordDto(drinkRecordEntity));
        }
        this.goal = record.getGoal();
    }
}
