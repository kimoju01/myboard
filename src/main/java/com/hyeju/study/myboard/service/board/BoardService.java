package com.hyeju.study.myboard.service.board;

import com.hyeju.study.myboard.domain.entity.BoardEntity;
import com.hyeju.study.myboard.domain.repository.BoardRepository;
import com.hyeju.study.myboard.dto.BoardDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public Long save(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    @Transactional
    public List<BoardDto> getList() {
        List<BoardEntity> board = boardRepository.findAll();
        
    }

}
