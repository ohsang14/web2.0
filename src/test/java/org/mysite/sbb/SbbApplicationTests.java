package org.mysite.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SbbApplicationTests {

    // Question 테이블에 CRUD 작업을 지원하는 객체를
    // DI(Dependency Injection) 받는다.
    @Autowired // 자동으로 객체를 만들어줌 (new 이런식 필요 없)
    private QuestionRepository questionRepository;

    @Test
    void testJpa01() {
        Question q1 = new Question();
        q1.setId(1);
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContect("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());

        this.questionRepository.save(q1);


        Question q2 = new Question();
        q2.setId(2);
        q2.setSubject("스프링 부트 모델 질문 입니다.");
        q2.setContect("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());

        this.questionRepository.save(q2);
    }


}
