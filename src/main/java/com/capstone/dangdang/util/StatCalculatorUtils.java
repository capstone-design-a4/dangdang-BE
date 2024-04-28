package com.capstone.dangdang.util;

import com.capstone.dangdang.dto.entity.DrinkDto;
import com.capstone.dangdang.dto.entity.DrinkRecordDto;
import com.capstone.dangdang.dto.entity.RecordDto;
import com.capstone.dangdang.dto.response.DayStatDto;
import com.capstone.dangdang.dto.response.MonthStatDto;
import com.capstone.dangdang.dto.response.WeekStatDto;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StatCalculatorUtils {


    /**
     * 성능 최적화 가능.
     * DB에서 필요한 데이터만 가져와야 함. 이건 너네가 하셈
     * @param recordDto
     */
    public static void getDayStat(RecordDto recordDto) {
        List<DrinkRecordDto> dayDrinkRecords = recordDto.getDrinkList().stream().filter(
                d -> {
                    return d.getCreateDate().getYear() == LocalDateTime.now().getYear() &&
                            d.getCreateDate().getDay() == LocalDateTime.now().getDayOfMonth();
                }
        ).collect(Collectors.toList());


        DayStatDto dayStatDto = calDayStat(dayDrinkRecords);
        recordDto.setDayStat(dayStatDto);
    }

    private static DayStatDto calDayStat(List<DrinkRecordDto> dayDrinkRecords) {
        float sugarIntakes = 0;
        float calorieIntakes = 0;
        for (DrinkRecordDto dayDrinkRecord : dayDrinkRecords) {
            DrinkDto drink = dayDrinkRecord.getDrink();
            sugarIntakes += drink.getSugar();
            calorieIntakes += drink.getCalorie();
        }

        DayStatDto dayStatDto = new DayStatDto(sugarIntakes, calorieIntakes);
        return dayStatDto;
    }


    public static void getWeekStat(RecordDto recordDto) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        List<DrinkRecordDto> weekDrinkRecords = recordDto.getDrinkList().stream().filter(
                d -> {
                    LocalDateTime createDate = d.getCreateDate().toLocalDateTime();
                    return d.getCreateDate().getYear() == LocalDateTime.now().getYear() &&
                            !createDate.isBefore(startOfWeek) && !createDate.isAfter(endOfWeek);
                }
        ).collect(Collectors.toList());

        WeekStatDto weekStatDto = calWeekStat(recordDto, startOfWeek, endOfWeek, weekDrinkRecords);
        recordDto.setWeekStat(weekStatDto);
    }

    private static WeekStatDto calWeekStat(RecordDto recordDto, LocalDateTime startOfWeek, LocalDateTime endOfWeek, List<DrinkRecordDto> weekDrinkRecords) {
        List<DayStatDto> dayStatDtos = new ArrayList<>();

        for(int day = startOfWeek.getDayOfMonth(); day <= endOfWeek.getDayOfMonth(); day++) {

            int finalDay = day;
            List<DrinkRecordDto> dayDrinkRecords = weekDrinkRecords.stream().filter(
                    d -> {
                        LocalDateTime localDateTime = d.getCreateDate().toLocalDateTime();
                        return d.getCreateDate().getYear() == LocalDateTime.now().getYear() &&
                                d.getCreateDate().getDay() == finalDay;
                    }
            ).collect(Collectors.toList());

            DayStatDto dayStat = calDayStat(dayDrinkRecords);
            dayStatDtos.add(dayStat);
        }


        float sugarIntakes = 0;
        float calorieIntakes = 0;
        int totalAchievedDay = 0;

        for (DayStatDto dayStatDto : dayStatDtos) {
            sugarIntakes += dayStatDto.getSugarIntake();
            calorieIntakes += dayStatDto.getCalorieIntake();
            if(sugarIntakes <= recordDto.getGoal()) {
                totalAchievedDay++;
            }
        }

        WeekStatDto weekStatDto = new WeekStatDto(sugarIntakes / dayStatDtos.size(), calorieIntakes , totalAchievedDay, dayStatDtos);
        return weekStatDto;
    }

    public static void getMonthStat(RecordDto recordDto) {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        LocalDateTime startOfMonth = now.with(TemporalAdjusters.firstDayOfMonth()).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime endOfMonth = now.with(TemporalAdjusters.lastDayOfMonth()).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        List<DrinkRecordDto> monthDrinkRecords = recordDto.getDrinkList().stream().filter(
                d -> d.getCreateDate().getYear() == year && d.getCreateDate().getMonth() == month
        ).collect(Collectors.toList());

        List<WeekStatDto> weekStats = new ArrayList<>();
        float sugarIntakes = 0;
        float calorieIntakes = 0;
        int totalAchievedDay = 0;
        int totalDate = 0;

        while (!startOfMonth.isAfter(endOfMonth)) {
            LocalDateTime finalStartOfWeek = startOfMonth;
            LocalDateTime finalEndOfWeek = startOfMonth.plusDays(6);

            List<DrinkRecordDto> weekDrinkRecords = monthDrinkRecords.stream().filter(
                    d -> {
                        return !d.getCreateDate().toLocalDateTime().isBefore(finalStartOfWeek) &&
                                !d.getCreateDate().toLocalDateTime().isAfter(finalEndOfWeek);
                    }
            ).collect(Collectors.toList());

            WeekStatDto weekStat = calWeekStat(recordDto, finalStartOfWeek, finalEndOfWeek, weekDrinkRecords);
            weekStats.add(weekStat);

            sugarIntakes += weekStat.getAvgSugarIntake();
            calorieIntakes += weekStat.getCalorieIntake();
            totalAchievedDay += weekStat.getTotalAchievedDay();
            totalDate += weekDrinkRecords.size();

            startOfMonth = startOfMonth.plusWeeks(1);
        }

        MonthStatDto monthStatDto = new MonthStatDto(sugarIntakes / totalDate, calorieIntakes, totalAchievedDay, weekStats);
        recordDto.setMonthStat(monthStatDto);
    }

}
