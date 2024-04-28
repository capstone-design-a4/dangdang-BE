package com.capstone.dangdang.controller;

import com.capstone.dangdang.dto.entity.DrinkRecordDto;
import com.capstone.dangdang.repository.DrinkRecordRepository;
import com.capstone.dangdang.service.BookmarkService;
import com.capstone.dangdang.service.DrinkRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Drink Record")
@RequestMapping("/api/drink-record")
public class DrinkRecordController {

    private final DrinkRecordService drinkRecordService;
    private final BookmarkService bookmarkService;

    @PostMapping
    @Operation(summary = "마신 음료 담기")
    public ResponseEntity addDrinkRecord(@RequestParam Long drinkId, Authentication authentication) {
        drinkRecordService.addDrinkRecord(drinkId, authentication);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    @Operation(description = "오늘 마신 음료 리스트 조회")
    public List<DrinkRecordDto> getDrinkRecordList(Authentication authentication) {
        List<DrinkRecordDto> todayDrinkRecords = drinkRecordService.getTodayDrinkRecords(authentication);
        return bookmarkService.fetchBookmarkToRecord(todayDrinkRecords, authentication);
    }

    @DeleteMapping
    @Operation(summary = "마신 음료 삭제")
    public ResponseEntity deleteDrinkRecord(@RequestParam Long drinkRecordId, Authentication authentication) {
        drinkRecordService.deleteDrinkRecord(drinkRecordId, authentication);
        return new ResponseEntity(HttpStatus.OK);
    }


}
