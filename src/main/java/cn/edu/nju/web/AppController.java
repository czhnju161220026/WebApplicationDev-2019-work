package cn.edu.nju.web;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@EnableAutoConfiguration
public class AppController {
    @GetMapping("/sign_up")
    public String signUp(){
        return "sign_up";
    }
}
