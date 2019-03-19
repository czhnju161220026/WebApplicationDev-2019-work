# 网络应用开发课程实践项目
## 想法
+ 使用SpringBoot框架(Web框架，内置了TomCat服务器) + Thymeleaf模板(动态页面) + MySql实现一个可以注册，可以登录的网站，网站具体功能待讨论。<br>
+ 开发语言为Java
+ 项目使用Maven构建工具。 

## 进度
目前尝试了一下springboot框架和简单的html，css样式表。<br>
过程中遇到了一点坑，就是静态资源(目前有css和js)的路径问题。<br>
查阅资料发现，springboot中提供了系统路径，包括static,templates等。在其中的文件可以直接引用，省去了寻址的麻烦。<br>
参见：[这篇博文](https://blog.csdn.net/yiifaa/article/details/78299052)和[这个例子](https://blog.csdn.net/qq_29314861/article/details/79497281) <br>
实现了电子邮件发送验证码的功能，但是发送速度很慢，页面要长时间等待跳转。考虑单独开启一个进程发送。
