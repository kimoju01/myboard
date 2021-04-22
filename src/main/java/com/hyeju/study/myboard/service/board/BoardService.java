package com.hyeju.study.myboard.service.board;

import com.hyeju.study.myboard.domain.entity.BoardEntity;
import com.hyeju.study.myboard.domain.repository.BoardRepository;
import com.hyeju.study.myboard.dto.BoardDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.collect;

@Service
@AllArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;

    /* 게시글 등록 */
    public Long save(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    /* 게시글 전체 조회 */
    public List<BoardDto> findAll() {
        return boardRepository.findAll().stream()
                .map(boardEntity -> BoardDto.builder()
                        .id(boardEntity.getId())
                        .writer(boardEntity.getWriter())
                        .title(boardEntity.getTitle())
                        .content(boardEntity.getContent())
                        .createdDate(boardEntity.getCreatedDate())
                        .build())
                .collect(Collectors.toList());

        //        List<BoardEntity> boardEntityList = boardRepository.findAll();
//        return boardEntityList.stream()
//                .map(boardEntity -> BoardDto.builder()
//                        .id(boardEntity.getId())
//                        .writer(boardEntity.getWriter())
//                        .title(boardEntity.getTitle())
//                        .content(boardEntity.getContent())
//                        .createdDate(boardEntity.getCreatedDate())
//                        .modifiedDate(boardEntity.getModifiedDate())
//                        .build())
//                .collect(Collectors.toList());

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

    /* 게시글 상세 조회 */
    public BoardDto getPost(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        BoardDto boardDto = BoardDto.builder()
                .id(boardEntity.getId())
                .writer(boardEntity.getWriter())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .createdDate(boardEntity.getCreatedDate())
                .build();
        return boardDto;

    }


}
