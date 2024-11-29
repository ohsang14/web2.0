package org.mysite.sbb.question;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mysite.sbb.answer.AnswerForm;
import org.mysite.sbb.user.SiteUser;
import org.mysite.sbb.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    /**
     * 질문 목록을 페이징 처리하여 표시
     */
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list"; // 질문 목록 페이지 렌더링
    }

    /**
     * 특정 질문의 상세 페이지를 표시
     */
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail"; // 질문 상세 페이지 렌더링
    }

    /**
     * 질문 작성 폼을 표시
     */
    @PreAuthorize("isAuthenticated()") // 로그인된 사용자만 접근 가능
    @GetMapping("/create")
    public String questionCreate(Model model) {
        model.addAttribute("questionForm", new QuestionForm()); // 빈 폼 객체를 모델에 추가
        return "question_form"; // 질문 등록 폼 페이지
    }

    /**
     * 질문 작성 요청 처리
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form"; // 유효성 검사 실패 시 폼 페이지로 이동
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
        return "redirect:/question/list"; // 저장 후 질문 목록 페이지로 리다이렉트
    }

    /**
     * 질문 수정 폼 표시
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String showModifyForm(@PathVariable("id") Integer id, Model model, Principal principal) {
        /*
         1. id에 해당하는 질문 객체를 DB에서 가져오기
         2. 로그인한 사용자와 작성자가 동일한지 확인
         3. 기존 질문 데이터를 폼에 채워 넣기
         4. 수정 폼 페이지 렌더링
         */
        Question question = this.questionService.getQuestion(id);

        // 작성자 확인
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정 권한이 없습니다.");
        }

        // 기존 데이터 폼에 채우기
        QuestionForm questionForm = new QuestionForm();
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        model.addAttribute("questionForm", questionForm);

        return "question_form"; // 수정 폼 페이지
    }

    /**
     * 질문 수정 요청 처리
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form"; // 유효성 검사 실패 시 폼 페이지로 이동
        }

        Question question = this.questionService.getQuestion(id);

        // 작성자 확인
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정 권한이 없습니다.");
        }

        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }

}
