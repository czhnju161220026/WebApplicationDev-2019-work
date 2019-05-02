package cn.edu.nju.web.webservice;

import cn.edu.nju.web.email.EmailSender;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@EnableAutoConfiguration
public class AppController {
    @GetMapping("/signup")
    public String signUp(){
        return "signup";
    }

    @GetMapping("/login")
    public String logIn() {
        return "login";
    }

    @PostMapping("/verify")
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
        //发送电子邮件

        ExecutorService sendService = Executors.newSingleThreadExecutor();
        sendService.execute(new Runnable() {
            @Override
            public void run() {
                EmailSender emailSender = new EmailSender();
                emailSender.sendVerificationCode(code.toString(), mail);
            }
        });
        sendService.shutdown();

        /*设置动态页面*/
        request.setAttribute("name",name);
        request.setAttribute("mail",mail);
        request.setAttribute("image","image/test.jpg");
        return "verify";
    }
}
