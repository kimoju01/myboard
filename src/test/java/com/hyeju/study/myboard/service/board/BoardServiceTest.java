package com.hyeju.study.myboard.service.board;

import com.hyeju.study.myboard.domain.repository.BoardRepository;
import com.hyeju.study.myboard.dto.BoardDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired BoardService boardService;
    @Autowired BoardRepository boardRepository;

    @Test
    void 게시글_등록() {
        //given
        BoardDto.builder()
                .writer("park")
                .title("test 제목입니당")
                .content("test 내용입니당")
                .build();
        //when

        //then
    }
}