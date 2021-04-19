package com.hyeju.study.myboard.service.board;

import com.hyeju.study.myboard.domain.entity.BoardEntity;
import com.hyeju.study.myboard.domain.repository.BoardRepository;
import com.hyeju.study.myboard.dto.BoardDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;

    public Long save(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    public List<BoardDto> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        return boardEntityList.stream()
                .map(boardEntity -> BoardDto.builder()
                        .id(boardEntity.getId())
                        .writer(boardEntity.getWriter())
                        .title(boardEntity.getTitle())
                        .content(boardEntity.getContent())
                        .createdDate(boardEntity.getCreatedDate())
                        .modifiedDate(boardEntity.getModifiedDate())
                        .build())
                .collect(Collectors.toList());

//        List<BoardDto> boardDtoList = new ArrayList<>();
//        for (BoardEntity boardEntity : boardEntityList) {
//            BoardDto boardDto = BoardDto.builder()
//                    .id(boardEntity.getId())
//                    .writer(boardEntity.getWriter())
//                    .title(boardEntity.getTitle())
//                    .content(boardEntity.getContent())
//                    .createdDate(boardEntity.getCreatedDate())
//                    .build();
//
//            boardDtoList.add(boardDto);
//        }
//
//        return boardDtoList;
    }


}
