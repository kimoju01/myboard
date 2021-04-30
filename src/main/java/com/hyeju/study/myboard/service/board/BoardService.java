package com.hyeju.study.myboard.service.board;

import com.hyeju.study.myboard.domain.entity.BoardEntity;
import com.hyeju.study.myboard.domain.repository.BoardRepository;
import com.hyeju.study.myboard.dto.BoardResponseDto;
import com.hyeju.study.myboard.dto.BoardSaveRequestDto;
import com.hyeju.study.myboard.dto.BoardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    /* 게시글 등록 */
    @Transactional
    public Long save(BoardSaveRequestDto requestDto) {
        return boardRepository.save(requestDto.toEntity()).getId();
    }

    /* 게시글 수정 */
    @Transactional
    public Long update(Long id, BoardUpdateRequestDto requestDto) {
        BoardEntity boardEntity = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. id=" + id));

        boardEntity.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    /*게시글 삭제 */
    @Transactional
    public void delete(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. id=" + id));

        boardRepository.delete(boardEntity);
    }

    /* 게시글 조회 */
    @Transactional(readOnly = true)
    public BoardResponseDto findById(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. id=" + id));

        return new BoardResponseDto(boardEntity);
    }

    /* 게시글 목록 */
    @Transactional(readOnly = true)
    public List<BoardResponseDto> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        return boardEntityList.stream()
                .map(boardEntity -> new BoardResponseDto(boardEntity))  //.map(BoardResponseDto::new)
                .collect(Collectors.toList());
    }
}
