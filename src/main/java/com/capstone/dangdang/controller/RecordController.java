package com.capstone.dangdang.controller;

import com.capstone.dangdang.dto.entity.RecordDto;
import com.capstone.dangdang.service.MemberService;
import com.capstone.dangdang.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "섭취량 통계")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/record")
public class RecordController {

    private final RecordService recordService;

    @GetMapping("/day")
    @Operation(summary = "일일 섭취량 통계")
    public RecordDto getDayRecord(Authentication auth) {
        return recordService.getMyDayRecord(auth);
    }

    @GetMapping("/week")
    @Operation(summary = "주간 섭취량 통계")
    public RecordDto getWeekRecord(Authentication auth) {
        return recordService.getMyWeekRecord(auth);
    }

    @GetMapping("/month")
    @Operation(summary = "월간 섭취량 통계")
    public RecordDto getMonthRecord(Authentication auth) {
        return recordService.getMyMonthRecord(auth);
    }

    @PutMapping("/goal")
    @Operation(summary = "섭취량 목표 설정")
    public RecordDto setMyGoal(@RequestParam int goal, Authentication auth) {
        return recordService.setMyGoal(goal, auth);
    }

}