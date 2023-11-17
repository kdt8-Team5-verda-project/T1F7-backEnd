package com.verda.BE.board.service;

import com.verda.BE.board.dto.requestdto.BoardRequestDTO;
import com.verda.BE.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardSerivce {
    private BoardRepository boardRepository;

    public BoardSerivce(BoardRepository boardRepository){
        this.boardRepository=boardRepository;
    }

    @Transactional
    public Long save(BoardRequestDTO requestDTO){
        return boardRepository.save(requestDTO.toEntity()).getPostId()  ;
    }


}
