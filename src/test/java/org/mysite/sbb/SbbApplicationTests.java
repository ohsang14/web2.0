package org.mysite.sbb;

import org.junit.jupiter.api.Test;
import org.mysite.sbb.answer.Answer;
import org.mysite.sbb.answer.AnswerRepository;
import org.mysite.sbb.question.Question;
import org.mysite.sbb.question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SbbApplicationTests {

    // Question 테이블에 CRUD 작업을 지원하는 객체를
    // DI(Dependency Injection) 받는다.
    @Autowired // 자동으로 객체를 만들어줌 (new 이런식 필요 없)
    private QuestionRepository questionRepository;

    // Answer 테이블에 CRUD 작업을 지원하는 객체를
    // DI (Dependency Injection) 받는다.
    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void testJpa01() {
//        Question q1 = new Question();
//        q1.setId(1);
//        q1.setSubject("sbb가 무엇인가요?");
//        q1.setContent("sbb에 대해서 알고 싶습니다.");
//        q1.setCreateDate(LocalDateTime.now());
//
//        this.questionRepository.save(q1);


        Question q2 = new Question();
        q2.setId(2);
        q2.setSubject("스프링 부트 모델 질문 입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());

        this.questionRepository.save(q2);

        for (int i = 0; i < 3; i++) {

            Question q = new Question();
            q.setId(i);
            q.setSubject("스프링 부트 모델 질문 ."+(i+1));
            q.setContent("id는 자동으로 생성되나요?"+(i+1));
            q.setCreateDate(LocalDateTime.now());

            this.questionRepository.save(q);

        }
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
        Question q = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
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
        /*
        id가 1번인 레코드의 제목(Subject)을 '수정된 제목'으로 변경
        SQL : update question set subject = '수정된 제목' where id = 1;
        Repository 메서드를 사용해서 위의 SQL문이 내부적으로 수행되도록 하려면
        1. 리포지토리의 findById 메서드를 해당 엔티티를 찾고
        2. 그 엔티티의 subject를 수정한 후
        3. 리포지토리의 save 메서드로 그 엔티티를 저장.
         */
        Optional<Question> oq = this.questionRepository.findById(1);
        assertTrue(oq.isPresent()); // isPresent - 물어보는거 , Present - 존재하다.
        Question q = oq.get();
        q.setSubject("수정된 제목");
        this.questionRepository.save(q);
    }

    @Test
    void testJpa08() {
        /*
        현재 question 테이블에 레코드가 2개 있는 것을 확인.
        id가 1번인 레코드를 삭제하고
        삭제 후에 question 테이블에 레코드가 1개인 것을 확인.
         */

        /*
        question 테이블에 레코드 수를 확인하는 SQL문
        => select count(*)  from question;
        Repository 메서드로는 count()를 사용하면 됨.
         */

        /*
        question 테이블에서 id가 1인 레코드 삭제하는 SQL문
        => delete from question where id = 1;
        1. Repository를 이용해서 레코드를 삭제하기 위해서는
        먼저 findById() 메서드 등을 통해 삭제하고자
        하는 entiry 객체를 찾고 :
            Optional<Question> oq = this.questionRepository.findById(1);
        2. 그 객체를 Repository의 delete 메서드의 인자로 전달해 호출하면된다.
        Question q = oq.get();
        this.questionRepository.delete(q);
         */
        assertEquals(2, this.questionRepository.count());
        Optional<Question> oq = this.questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        this.questionRepository.delete(q);
        assertEquals(1, this.questionRepository.count());


    }

    @Test
    void testJpa09() {
        Optional<Question> oq = this.questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q);
        a.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a);
    }

    @Test
    void testJpa10() {
        /*
            2번 질문에 대한 답변 레코드를 저장하자.
            답변 레코드는 Answer 테이블에 저장되고,
            SQL: insert into answer(content, createdate, question_id)
                values('~답변', '2024/09/30', 2);
                주의: question_id의 값은 반드시 question 테이블에 존재하는 레코드의 id 칼럼 값이어야 한다.
                그렇지 않으면 참조 무결성 오류가 발생해서 레코드가 삽입되지 않는다. referentibl integrity violation.
            answer 테이블에 저장하기 위해서는
            AnswerRepository를 사용해야 한다.
            1. 질문 엔티티 객체를 찾는다.
                Optional<Question> oq = this.questionRepository.findById(2);
            2. 답변 엔티티 객체를 생성한다.
                Answer answer = new Answer();
            3. 답변 엔티티 객체의 프로퍼티를 설정한다.
                answer.setContent("~~~~답변입니다.");ß
                answer.setCreateData(LocalDateTime.now());
                // 이답변에 연결된 질문 객체 설정
                answer.seQuestion(oq.get());
            4. AnswerRepository의 save() 메서드를 호출해 DB에 저장
                this.answerRepository.save(answer);
         */
        Optional<Answer> oa = this.answerRepository.findById(1);
        assertTrue(oa.isPresent());
        Answer a = oa.get();
        assertEquals(2, a.getQuestion().getId());
    }

    @Test
    void testJpa11() {
        Optional<Question> oq = this.questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        List<Answer> answerList = q.getAnswerList();

        assertEquals(1, answerList.size());
        assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());

    }
}
