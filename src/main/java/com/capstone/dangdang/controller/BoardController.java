package com.capstone.dangdang.controller;

import com.capstone.dangdang.dto.entity.BoardDto;
import com.capstone.dangdang.dto.request.BoardRequest;
import com.capstone.dangdang.dto.request.BoardUpdateRequest;
import com.capstone.dangdang.enums.CafeName;
import com.capstone.dangdang.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시글")
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    @Operation(summary = "게시글 생성")
    public BoardDto create(@RequestBody BoardRequest boardRequest, Authentication authentication) {
        return boardService.create(boardRequest, authentication);
    }

    @PutMapping
    @Operation(summary = "게시글 수정")
    public BoardDto update(@RequestBody BoardUpdateRequest updateRequest, Authentication authentication) {
        return boardService.update(updateRequest, authentication);
    }

    @DeleteMapping
    @Operation(summary = "게시글 삭제")
    public ResponseEntity delete(@RequestParam Long boardId, Authentication authentication) {
        boardService.delete(boardId, authentication);
        return new ResponseEntity(HttpStatus.OK);
    }


    @PostMapping("/recommend")
    @Operation(summary = "게시글 추천")
    public BoardDto recommend(@RequestParam Long boardId, Authentication authentication) {
        return boardService.recommend(boardId, authentication);
    }

    @GetMapping("/search")
    @Operation(summary = "게시글 검색", description = "제목, 내용, 카테고리로 검색, 페이징. pageNo는 0부터 시작")
    public ResponseEntity search(@RequestParam String searchContextCond,
                                 @RequestParam(defaultValue = "스타벅스") CafeName searchCategoryCond,
                                 @RequestParam(defaultValue = "0") int pageNo) {
        return new ResponseEntity(boardService.search(searchContextCond, searchCategoryCond, pageNo), HttpStatus.OK);
    }




}
