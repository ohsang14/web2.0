package org.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return "Hello Spring Boot Board";
    }
    @GetMapping("/jump")
    @ResponseBody
    public String helloJump(){
        return "점프 투 스프링 부트";
    }
}
