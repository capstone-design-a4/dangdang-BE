package com.capstone.dangdang.controller;

import com.capstone.dangdang.dto.BoardCreationRequest;
import com.capstone.dangdang.dto.BoardListResponse;
import com.capstone.dangdang.entity.BoardEntity;
import com.capstone.dangdang.repository.BoardRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardRepository boardRepository;



    //게시글 생성
    @PostMapping("/create")
    @ApiOperation(value = "게시글 작성 폼", notes = "제목, 내용, 카테고리 입력 후 submit")
    public ResponseEntity<BoardEntity> createBoard(@RequestBody BoardCreationRequest request){

        BoardEntity boardEntity = request.toEntity();


        BoardEntity saved = boardRepository.save(boardEntity);


        return ResponseEntity.ok(saved);
    }

    //게시글 전체 조회

    @GetMapping("/list")
    public ResponseEntity<List<BoardListResponse>> getAllBoards() {
        // BoardEntity의 리스트를 조회
        List<BoardEntity> boards = boardRepository.findAll();

        // BoardEntity의 리스트를 BoardListResponse의 리스트로 변환
        List<BoardListResponse> response = boards.stream().map(board -> {
            BoardListResponse dto = new BoardListResponse();
            dto.setId(board.getId());
            dto.setTitle(board.getTitle());
            dto.setName(board.getName());
            dto.setView(board.getView());
            dto.setSub(board.getSub());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

}
