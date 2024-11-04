package org.mysite.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        // id에 해당하는 Question 객체를 DB에서 인출하고
        Question question = this.questionService.getQuestion(id);
        // 그 객체를 model에 넣어준다.
        model.addAttribute("question", question);
        return "question_detail";
    }
    @GetMapping("/create")
    public String questionCreate(){
        return "question_form";
    }
    @PostMapping("/create")
    public String questionCreate(@RequestParam(value="subject")String subject,
                                 @RequestParam(value="content")String content){
        this.questionService.create(subject,content);
        return "redirect:/question/list";
    }
}
