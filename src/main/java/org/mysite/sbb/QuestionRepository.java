package org.mysite.sbb;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findBySubject(String subject);
    Question findBySubjectAndContect(String subject, String contect);
    List<Question> findBySubjectLike(String subject);
}
