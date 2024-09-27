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

    @Test
    void testJpa02() {
        // question 테이블에 있는 모든 레코드들을 읽고
        // 각 레코드들의 Question 객체로 변환한 후에
        // 그 객체들을 원소로 가지는 List 객체를 생성한다.

        List<Question> all = this.questionRepository.findAll();
        assertEquals(2, all.size());

        Question q = all.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

    @Test
    void testJpa03() {
       /*
         question 테이블에서 id가 1인 레코드를 읽고
         그 레코드를 Question 객체로 만들고자 한다.
        */
        Optional<Question> oq = this.questionRepository.findById(1);
        if (oq.isPresent()) {
            Question q = oq.get();
            assertEquals("sbb가 무엇인가요?", q.getSubject());
        }
    }

    @Test
    void testJpa04() {
        /**
         특정 제목(subject)를 가진 레코드를 읽고
         그 레코드를 Question 객체로 반환.
         findBySubject() 메서드를 이용해야 하나
         Repository는 이 메서드를 기본으로 제공하지 않는다.
         따라서, 우리가(개발자가) Repository에 이 메서드를 추가해줘야 한다.
         */

        Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
        assertEquals(1, q.getId());
    }

    @Test
    void testJpa05() {
        Question q = this.questionRepository.findBySubjectAndContect("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        assertEquals(1, q.getId());
    }

    @Test
    void testJpa06() {
        List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
        Question q = qList.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

    @Test
    void testJpa07() {
        Optional<Question> oq = this.questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        q.setSubject("수정된 제목");
        this.questionRepository.save(q);
    }
    @Test
    void testJpa08(){
        assertEquals(2, this.questionRepository.count());
        Optional<Question> oq = this.questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        this.questionRepository.delete(q);
        assertEquals(1,this.questionRepository.count());
    }

}
