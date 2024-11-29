package org.mysite.sbb.question;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.mysite.sbb.answer.Answer;
import org.mysite.sbb.user.SiteUser;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Question {
    @Id // 질문 고유번호
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject; // 질문 제목

    @Column(columnDefinition =  "TEXT")
    private String content; // 질문 내용, 수정된 부분

    private LocalDateTime createDate; // 질문 등록 날짜 및 시각

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime localDateTime;


}
