package cn.edu.nju.web.webservice;

import cn.edu.nju.web.database.DatabaseServer;
import cn.edu.nju.web.email.EmailSender;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@EnableAutoConfiguration
public class AppController {

    //主页
    @RequestMapping(value = "/",method= RequestMethod.GET )
    public String index(HttpServletRequest request) {
        return "index";
    }

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

    //通过GetMapping获取文章类型，然后返回相关类别文章的动态页面
    @GetMapping(value="/articles")
    public String articles(HttpServletRequest request) {
        String category = request.getParameter("category");
        //TODO: 在DatabaseServer中添加返回相关文章的方法
        if(category.equals("tech")) {
            request.setAttribute("title","爱科技");
            request.setAttribute("category","爱科技");
            request.setAttribute("introduce","获取科技前沿新闻");
            try {
                request.setAttribute("articles", DatabaseServer.getArticlesByCategory("科技"));
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(category.equals("sport")) {
            request.setAttribute("title","体育前线");
            request.setAttribute("category","体育前线");
            request.setAttribute("introduce","关注体育新闻，更高、更快、更强");
            try {
                request.setAttribute("articles", DatabaseServer.getArticlesByCategory("体育"));
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(category.equals("economics")) {
            request.setAttribute("title","经济达人");
            request.setAttribute("category","经济达人");
            request.setAttribute("introduce","帮助你成为理财高手");
            try {
                request.setAttribute("articles", DatabaseServer.getArticlesByCategory("经济"));
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return "articles";
    }

    //通过GetMapping获取文章id，然后用request set加载文章内容
    @GetMapping(value="/article")
    public String article(HttpServletRequest request) {
        String id = request.getParameter("id");
        System.out.println(id);
        request.setAttribute("title","测试1");
        request.setAttribute("id",id);
        return "article";
    }
}
