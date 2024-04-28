package com.capstone.dangdang.controller;

import com.capstone.dangdang.dto.entity.DrinkDto;
import com.capstone.dangdang.enums.CafeName;
import com.capstone.dangdang.enums.DrinkSortCond;
import com.capstone.dangdang.service.BookmarkService;
import com.capstone.dangdang.service.DrinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Drink")
@RequestMapping("/api/drink")
public class DrinkController {

    private final DrinkService drinkService;
    private final BookmarkService bookmarkService;


    @GetMapping("/list/{cafeName}")
    @Operation(summary = "음료 목록 조회")
    public List<DrinkDto> getDrinkList(@PathVariable CafeName cafeName, Authentication authentication) {
        List<DrinkDto> drinkList = drinkService.getDrinkList(cafeName);
        return bookmarkService.fetchBookmarkToDrink(drinkList, authentication);
    }

    @GetMapping("/most-bookmarked-list")
    @Operation(summary = "음료 추천 4개(즐겨찾기 많은 순)")
    public List<DrinkDto> getRecommendDrinkList() {
        return drinkService.getRecommendDrinkList();
    }

    @GetMapping(value = "/image/{drinkId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Operation(summary = "음료 이미지 조회")
    public Resource getCafeImage(@PathVariable Long drinkId) {
        return drinkService.getDrinkImage(drinkId);
    }

    @GetMapping("/search")
    @Operation(summary = "음료 검색, 정렬")
    public Page<DrinkDto> search(@RequestParam CafeName cafeName,
                                 @RequestParam(defaultValue = "SUGAR") DrinkSortCond sortCond,
                                 @RequestParam(defaultValue = "") String searchCond,
                                 @RequestParam(defaultValue = "0") int pageNo,
                                 Authentication authentication) {
        return drinkService.searchDrink(cafeName, searchCond, sortCond, pageNo, authentication);
    }

    @GetMapping("/bookmark")
    @Operation(summary = "유저가 즐겨찾기 한 음료 리스트 조회")
    public List<DrinkDto> getBookmarkedDrinkList(Authentication authentication) {
        return bookmarkService.getBookmarkedDrinkList(authentication);
    }


}
