package com.capstone.dangdang.controller;

import com.capstone.dangdang.dto.entity.BoardDto;
import com.capstone.dangdang.dto.request.CommentRequest;
import com.capstone.dangdang.service.BoardService;
import com.capstone.dangdang.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "댓글")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;

    @PostMapping
    @Operation(summary = "댓글 작성")
    public BoardDto create(@Valid @RequestBody CommentRequest commentRequest, Authentication authentication) {
        commentService.create(commentRequest, authentication);
        return boardService.get(commentRequest.getBoardId());
    }

    @DeleteMapping
    @Operation(summary = "댓글 삭제")
    public BoardDto delete(@RequestParam Long commentId, @RequestParam Long boardId, Authentication authentication) {
        commentService.delete(commentId, authentication);
        return boardService.get(boardId);
    }


}
