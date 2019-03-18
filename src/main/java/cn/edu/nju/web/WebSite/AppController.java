package cn.edu.nju.web.WebSite;

import cn.edu.nju.web.Email.EmailSender;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;
import java.util.Timer;

@Controller
@EnableAutoConfiguration
public class AppController {
    @GetMapping("/sign_up")
    public String signUp(){
        return "sign_up";
    }

    @GetMapping("/log_in")
    public String logIn() {
        return "log_in";
    }

    @PostMapping("/input_verification_code")
    public String inputVerificationCode(HttpServletRequest request) {
        /*获得提交的表单信息*/
        String name = request.getParameter("name");
        String mail = request.getParameter("mail");
        String pwd1 = request.getParameter("pwd1");
        String pwd2 = request.getParameter("pwd2");

        //TODO: 应该有一个EmailSender类，完成发送验证码的工作
        StringBuilder code = new StringBuilder("");
        Random random = new Random(System.nanoTime());
        for(int i = 0;i < 6;i++) {
            code.append(random.nextInt(10));
        }
        System.out.println(code.toString());
        EmailSender emailSender = new EmailSender();
        emailSender.sendVerificationCode(code.toString(), mail);

        /*设置动态页面*/
        request.setAttribute("name",name);
        request.setAttribute("mail",mail);
        return "input_verification_code";
    }
}
