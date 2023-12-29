package org.choongang.controllers.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@Getter @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class  Base {

    @CreatedDate
    @Column(updatable = false)      // 수정불가
    private LocalDateTime createdAt; // 가입일자

    @LastModifiedDate
    @Column(insertable = false)      // 수정할때 입력
    private LocalDateTime modifiedat; // 마지막 수정 일자


}
