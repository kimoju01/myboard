package com.hyeju.study.myboard.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass   //테이블로 매핑 X 자식 entity에게 매핑 정보를 상속하기 위함
@EntityListeners(AuditingEntityListener.class)  //JPA Auditing 기능 사용 위함(시간 자동으로 넣는 기능)
public abstract class TimeEntity {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
