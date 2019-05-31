package cn.edu.nju.web.webservice;

import cn.edu.nju.web.database.DatabaseServer;
import cn.edu.nju.web.email.EmailSender;
import cn.edu.nju.web.util.Article;

import cn.edu.nju.web.util.MD5Util;
import cn.edu.nju.web.util.User;
import cn.edu.nju.web.util.Comment;
import cn.edu.nju.web.util.Page;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@EnableAutoConfiguration
public class AppController {
	//Get和Post都能从服务器获取数据，但是get获取数据通过地址栏参数，post通过报文，因此post更适合需要加密的场合
	//post可以向服务器发送数据
	//主页
	@GetMapping("/")
	public String index(HttpServletRequest request) {
		return "index";
	}


    @PostMapping("/signup")
    public String verifySignUpInfo(HttpServletRequest request, Map<String, Object> map) {
        /*获得提交的表单信息*/
        String name = request.getParameter("name");
        String mail = request.getParameter("mail");
        String pwd1 = request.getParameter("pwd1");
        String pwd2 = request.getParameter("pwd2");

        /* 判断用户名或密码是否已经存在 */
        boolean nameExist = true, mailExist = true;
        try {
            nameExist = DatabaseServer.isNameExist(name);
            mailExist = DatabaseServer.isEmailExist(mail);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (nameExist && mailExist) {
            map.put("msg", "邮箱和用户名已注册");
            return "signup";
        }
        else if (nameExist) {
            map.put("msg", "用户名已存在");
            return "signup";
        }
        else if (mailExist) {
            map.put("msg", "邮箱已注册");
            return "signup";
        }
        else {
            // TODO: 新建一个未激活用户插入用户表, 然后发送验证邮件
            int uid = mail.hashCode() & 0x7fffffff;
            String code = MD5Util.MD5Encode(mail, "utf8");
            User user = new User(uid, name, mail, pwd1, code);
            try {
                DatabaseServer.addUser(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            StringBuilder code = new StringBuilder("");
//            Random random = new Random(System.nanoTime());
//            for(int i = 0;i < 6;i++) {
//                code.append(random.nextInt(10));
//            }
//            System.out.println(code.toString());
//            //发送电子邮件
//
            ExecutorService sendService = Executors.newSingleThreadExecutor();
            sendService.execute(new Runnable() {
                @Override
                public void run() {
                    EmailSender emailSender = new EmailSender();
                    emailSender.sendVerificationCode(code, mail);
                }
            });
            sendService.shutdown();

//            request.setAttribute("name", name);
//            request.setAttribute("mail", mail);
//            String md5Code = MD5Util.MD5Encode(code.toString(), "utf8");
//            request.setAttribute("code", md5Code);
//            request.setAttribute("pwd", pwd1);
            request.setAttribute("mail", mail);
            return "verify";
        }
    }

    @GetMapping("/login")
    public String logIn() {
        return "login";
    }


    @RequestMapping(value = "/checkCode")
    public String checkCode(String code){
        boolean success = false;
        try {
            success = DatabaseServer.activateUserByCode(code);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (success) {
            return "activateSuccess";
        }

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
		for (int i = 0; i < 6; i++) {
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
		request.setAttribute("name", name);
		request.setAttribute("mail", mail);
		request.setAttribute("image", "image/test.jpg");
		return "verify";
	}

	//通过GetMapping获取文章类型，然后返回相关类别文章的动态页面
	@GetMapping(value = "/articles")
	public String articles(HttpServletRequest request) {
		String category = request.getParameter("category");
		int page = Integer.parseInt(request.getParameter("page"));
		//TODO: 在DatabaseServer中添加返回相关文章的方法
		if (category.equals("tech")) {
			request.setAttribute("title", "爱科技");
			request.setAttribute("category", "爱科技");
			request.setAttribute("introduce", "获取科技前沿新闻");
			try {
				List<Article> articles = DatabaseServer.getArticlesByCategory("科技");
				List<Page> pages = Page.getPages("tech", articles);
				request.setAttribute("pages", pages);
				int begin = (page - 1) * 10;
                int end = Math.min(page * 10, articles.size());
                request.setAttribute("articles", articles.subList(begin, end));
                if(page != 1) {
                	request.setAttribute("lastPage", "/articles?category=tech&page="+(page-1));
				}
                if(page != pages.size()) {
					request.setAttribute("nextPage", "/articles?category=tech&page="+(page+1));
				}
                request.setAttribute("currentPage", ""+page);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (category.equals("sport")) {
			request.setAttribute("title", "体育前线");
			request.setAttribute("category", "体育前线");
			request.setAttribute("introduce", "关注体育新闻，更高、更快、更强");
			try {
				List<Article> articles = DatabaseServer.getArticlesByCategory("体育");
				List<Page> pages = Page.getPages("sport", articles);
				request.setAttribute("pages", pages);
				int begin = (page - 1) * 10;
				int end = Math.min(page * 10, articles.size());
				request.setAttribute("articles", articles.subList(begin, end));
				if(page != 1) {
					request.setAttribute("lastPage", "/articles?category=sport&page="+(page-1));
				}
				if(page != pages.size()) {
					request.setAttribute("nextPage", "/articles?category=sport&page="+(page+1));
				}
				request.setAttribute("currentPage", ""+page);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (category.equals("economics")) {
			request.setAttribute("title", "经济达人");
			request.setAttribute("category", "经济达人");
			request.setAttribute("introduce", "帮助你成为理财高手");
			try {
				List<Article> articles = DatabaseServer.getArticlesByCategory("经济");
				List<Page> pages = Page.getPages("economics", articles);
				request.setAttribute("pages", pages);
				int begin = (page - 1) * 10;
				int end = Math.min(page * 10, articles.size());
				request.setAttribute("articles", articles.subList(begin, end));
				if(page != 1) {
					request.setAttribute("lastPage", "/articles?category=economics&page="+(page-1));
				}
				if(page != pages.size()) {
					request.setAttribute("nextPage", "/articles?category=economics&page="+(page+1));
				}
				request.setAttribute("currentPage", ""+page);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return "articles";
	}

	//通过GetMapping获取文章id，然后用request set加载文章内容
	@GetMapping(value = "/article")
	public String article(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			//文章内容
			Article article = DatabaseServer.getArticleById(id);
			request.setAttribute("article", article);
			//评论区
			List<Comment> comments = DatabaseServer.getCommentsByID(id);
			request.setAttribute("coms", comments);
			//
		} catch (SQLException e) {
			e.printStackTrace();
			//添加异常处理
		}
		return "article";
	}

	@PostMapping(path = "/sendComment")
	public String sendComment(HttpServletRequest request) {
		//TODO:获取用户登录状态
		boolean login = true;
		if(!login) {
			return "redirect:/login";
		}
		String id = request.getParameter("articleID");
		String content = request.getParameter("content");
		try {
			//TODO:获取用户ID
			int userid = 33;
			DatabaseServer.addComment(userid, Integer.parseInt(id), content);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "redirect:/article?id="+id;
	}
}
