package cn.edu.nju.web.email;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class EmailSender {
    private String username = "woaiguowuzui@126.com";
    private String password = "web123456";
    private String serverHost="smtp.126.com";
    private int port = 25;
    private String protocol = "smtp";

    public void sendVerificationCode(String code, String address) {
        //System.out.println("send "+code+" to "+address);
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(serverHost);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setProtocol(protocol);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(address);
        message.setSubject("激活邮件");
        //message.setText("Your verification code is: "+code);
        message.setText("请访问:\"http://localhost:8080/checkCode?code="+code+"\""+ "激活账号");
        mailSender.send(message);
        System.out.println("Send verification code:"+code+" to "+address+" successfully. ");
    }
}
