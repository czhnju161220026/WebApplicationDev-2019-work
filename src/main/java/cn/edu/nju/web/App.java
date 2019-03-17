package cn.edu.nju.web;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class App {
    public static void main(String[] args) {
        SpringApplication.run(AppController.class,args);
    }
}
