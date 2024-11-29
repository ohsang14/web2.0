package org.mysite.sbb.answer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.mysite.sbb.question.Question;
import org.mysite.sbb.user.SiteUser;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Question question;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime localDateTime;
}
