package com.capstone.dangdang.service;

import com.capstone.dangdang.dto.entity.BoardDto;
import com.capstone.dangdang.dto.request.CommentRequest;
import com.capstone.dangdang.entity.BoardEntity;
import com.capstone.dangdang.entity.CommentEntity;
import com.capstone.dangdang.entity.MemberEntity;
import com.capstone.dangdang.error.CustomException;
import com.capstone.dangdang.error.ErrorCode;
import com.capstone.dangdang.repository.BoardRepository;
import com.capstone.dangdang.repository.CommentRepository;
import com.capstone.dangdang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public BoardDto create(CommentRequest commentRequest, Authentication authentication) {
        System.out.println(commentRequest);
        MemberEntity memberEntity = memberRepository.findByLoginId(authentication.getName()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        BoardEntity boardEntity = boardRepository.findById(commentRequest.getBoardId()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        CommentEntity commentEntity = CommentEntity.builder()
                .board(boardEntity)
                .member(memberEntity)
                .content(commentRequest.getComment())
                .build();

        commentEntity = commentRepository.save(commentEntity);

        boardEntity.addComment(commentEntity);
        return new BoardDto(boardEntity);
    }

    @Transactional
    public void delete(Long commentId, Authentication authentication) {
        MemberEntity memberEntity = memberRepository.findByLoginId(authentication.getName()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        if(!commentEntity.getMember().equals(memberEntity)) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        commentRepository.delete(commentEntity);
    }

}
