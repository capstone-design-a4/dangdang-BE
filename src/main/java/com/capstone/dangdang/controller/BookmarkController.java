package com.capstone.dangdang.controller;

import com.capstone.dangdang.dto.entity.DrinkDto;
import com.capstone.dangdang.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "BookMark")
@RequestMapping("/api/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    @Operation(summary = "즐겨찾기 추가", description = "음료에서 하트 버튼 누를 때 동작")
    public ResponseEntity addBookmark(@RequestParam Long drinkId, Authentication authentication) {
        bookmarkService.add(drinkId, authentication);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping
    @Operation(summary = "즐겨찾기 취소", description = "즐겨찾기 된 음료를 취소")
    public ResponseEntity cancelBookmark(@RequestParam Long drinkId, Authentication authentication) {
        bookmarkService.cancel(drinkId, authentication);
        return new ResponseEntity(HttpStatus.OK);
    }

}
