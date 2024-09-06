package org.mysite.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SbbApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void testJpa(){
       List<Question> all = this.questionRepository.findAll();
       assertEquals(8, all.size());

       Question q = all.get(0);
       assertEquals("sbb가 무엇인가요? ",q.getSubject());
    }

}
