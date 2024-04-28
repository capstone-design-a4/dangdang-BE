package com.capstone.dangdang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MonthStatDto {

    private float avgSugarIntake;
    private float calorieIntake;
    private int totalAchievedDay;
    private List<WeekStatDto> weekStatList;
}
