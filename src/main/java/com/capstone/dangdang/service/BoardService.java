package com.capstone.dangdang.service;

import com.capstone.dangdang.dto.entity.BoardDto;
import com.capstone.dangdang.dto.entity.MemberDto;
import com.capstone.dangdang.dto.request.BoardRequest;
import com.capstone.dangdang.dto.request.BoardUpdateRequest;
import com.capstone.dangdang.entity.BoardEntity;
import com.capstone.dangdang.entity.MemberEntity;
import com.capstone.dangdang.enums.CafeName;
import com.capstone.dangdang.error.CustomException;
import com.capstone.dangdang.error.ErrorCode;
import com.capstone.dangdang.repository.BoardRepository;
import com.capstone.dangdang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private static final int PAGE_COUNT = 10;

    @Transactional
    public BoardDto create(BoardRequest boardRequest, Authentication authentication) {

        MemberEntity memberEntity = memberRepository.findByLoginId(authentication.getName()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        BoardEntity board = BoardEntity.builder()
                .member(memberEntity)
                .view(0L)
                .title(boardRequest.getTitle())
                .commentList(new ArrayList<>())
                .content(boardRequest.getContent())
                .category(boardRequest.getCategory())
                .build();

        return new BoardDto(boardRepository.save(board));
    }

    @Transactional
    public BoardDto update(BoardUpdateRequest updateRequest, Authentication authentication) {

        MemberEntity memberEntity = memberRepository.findByLoginId(authentication.getName()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        BoardEntity boardEntity = boardRepository.findById(updateRequest.getBoardId()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        if (!boardEntity.getMember().equals(memberEntity)) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        if(StringUtils.hasText(updateRequest.getContent())) {
            boardEntity.setContent(updateRequest.getContent());
        }

        if(StringUtils.hasText(updateRequest.getTitle())) {
            boardEntity.setTitle(updateRequest.getTitle());
        }

        return new BoardDto(boardRepository.save(boardEntity));
    }

    @Transactional(readOnly = true)
    public Page<BoardDto> search(String searchContextCond, CafeName searchCategoryCond, int pageNo) {
        PageRequest pageRequest = PageRequest.of(pageNo, PAGE_COUNT);
        Page<BoardEntity> boardEntityPage = boardRepository.searchByTitleOrContentAndCategory(
                searchContextCond, searchCategoryCond, pageRequest);

        return boardEntityPage.map(board -> new BoardDto(board));
    }


    @Transactional
    public void delete(Long boardId, Authentication authentication) {
        MemberEntity memberEntity = memberRepository.findByLoginId(authentication.getName()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        BoardEntity boardEntity = boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        if (!boardEntity.getMember().equals(memberEntity)) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        boardRepository.deleteById(boardId);
    }

    @Transactional
    public BoardDto recommend(Long boardId, Authentication authentication) {
        MemberEntity memberEntity = memberRepository.findByLoginId(authentication.getName()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        BoardEntity boardEntity = boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        List<Long> likeMemberIdList = boardEntity.getLikeMemberIdList();
        if (likeMemberIdList.contains(memberEntity.getId())) {
            throw new CustomException(ErrorCode.ALREADY_RECOMMEND_BOARD);
        }

        likeMemberIdList.add(memberEntity.getId());

        return new BoardDto(boardEntity);
    }


    @Transactional(readOnly = true)
    public BoardDto get(Long boardId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        return new BoardDto(boardEntity);
    }
}
