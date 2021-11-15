//package com.hyeju.study.myboard.domain.repository;
//
//import com.hyeju.study.myboard.domain.board.entity.BoardEntity;
//import com.hyeju.study.myboard.domain.board.repository.BoardRepository;
//import com.hyeju.study.myboard.domain.member.entity.MemberEntity;
//import com.hyeju.study.myboard.domain.member.repository.MemberRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//class BoardRepositoryTest {
//
//    @Autowired
//    BoardRepository boardRepository;
//
//    @AfterEach
//    public void cleanup() {
//        boardRepository.deleteAll();
//    }
//
//    @Test
//    public void 게시글저장_불러오기() {
//        //given
//        String title = "테스트 게시글 제목";
//        String content = "테스트 게시글 내용";
//
//        boardRepository.save(BoardEntity.builder()
//                .title(title)
//                .content(content)
//                .build());
//        //when
//        List<BoardEntity> boardEntityList = boardRepository.findAll();
//        //then
//        BoardEntity boardEntity = boardEntityList.get(0);
//        Assertions.assertThat(boardEntity.getTitle()).isEqualTo(title);
//        Assertions.assertThat(boardEntity.getContent()).isEqualTo(content);
//
//    }
//
//    @Test
//    public void TimeEntity_등록() {
//        //given
//        LocalDateTime now = LocalDateTime.of(2021,04,26,0,0,0);
//        boardRepository.save(BoardEntity.builder()
//                .title("title")
//                .content("content")
//                .build());
//        //when
//        List<BoardEntity> boardEntityList = boardRepository.findAll();
//        //then
//        BoardEntity boardEntity = boardEntityList.get(0);
//        System.out.println("createDate =" + boardEntity.getCreatedDate() + ", modifiedDate =" + boardEntity.getModifiedDate());
//
//        Assertions.assertThat(boardEntity.getCreatedDate()).isAfter(now);
//        Assertions.assertThat(boardEntity.getModifiedDate()).isAfter(now);
//
//    }
//
//}