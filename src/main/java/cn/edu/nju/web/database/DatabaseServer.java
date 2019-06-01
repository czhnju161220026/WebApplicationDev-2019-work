package cn.edu.nju.web.database;

import cn.edu.nju.web.util.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.TimeZone;


public class DatabaseServer {
	private static String DBDriver = "com.mysql.cj.jdbc.Driver";
	private static String DBUrl = "jdbc:mysql://localhost:3306/web_db?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC";
	private static String userName = "webuser";
	private static String passwd = "webuser";

	//根据文章ID返回文章内容
	public static Article getArticleById(int id) throws SQLException{

		Article article = new Article();
		Connection connection = null;
		try {
			Class.forName(DBDriver);
			connection = DriverManager.getConnection(DBUrl,userName,passwd);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * from NEWS where NID="+id);
			resultSet.next();
			String header = resultSet.getString("HEADER");
			String date = resultSet.getString("NTIME");
			String content = resultSet.getString("CONTENT");
			String upstream = resultSet.getString("UPSTREAM");
			article.setContent(content);
			article.setHeader(header);
			article.setUpstream(upstream);
			article.setTime( new SimpleDateFormat("yyyy-MM-dd").parse(date));
			article.setId(id);
		}
		//TODO: 添加找不到文章时的异常处理
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			connection.close();
		}

		return article;
	}

	public static List<Article> getArticlesByCategory(String category) throws SQLException{
		ArrayList<Article> results = new ArrayList<>();
		Connection connection = null;
		try {
			Class.forName(DBDriver);
			connection = DriverManager.getConnection(DBUrl,userName,passwd);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * from NEWS where CATEGORY='"+category+"' order by NTIME desc" );
			while (resultSet.next()) {
				Article article = new Article();
				article.setId(resultSet.getInt("NID"));
				article.setHeader(resultSet.getString("HEADER"));
				article.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(resultSet.getString("NTIME")));
				article.setUpstream(resultSet.getString("UPSTREAM"));
				article.setContent(resultSet.getString("CONTENT"));
				article.setUrl("article?id="+article.getId());
				results.add(article);
			}
		}
		//TODO: 添加找不到文章时的异常处理
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			connection.close();
		}
		return results;
	}

	public static boolean isEmailExist(String email) throws SQLException {
        Connection connection = null;
        try {
            Class.forName(DBDriver);
            connection = DriverManager.getConnection(DBUrl,userName,passwd);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from USER where email='"+email+"'");
            return resultSet.next();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally {
            connection.close();
        }
    }

    public static boolean isNameExist(String name) throws SQLException {
        Connection connection = null;
        try {
            Class.forName(DBDriver);
            connection = DriverManager.getConnection(DBUrl,userName,passwd);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from USER where UNAME='"+name+"'");
            return  resultSet.next();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally {
            connection.close();
        }
    }

    //查询指定用户的密码是否相同
	public static boolean isPwdCorrect(String name,String pwd) throws SQLException {
		Connection connection = null;
		try {
			Class.forName(DBDriver);
			connection = DriverManager.getConnection(DBUrl,userName,passwd);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT PWD from USER where UNAME='"+name+"'");
			//之前已经验证过用户名存在
			resultSet.next();
			String correctPwd = resultSet.getString("PWD");
			return correctPwd.equals(pwd);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally {
			connection.close();
		}
	}

    public static void addUser(User user) throws SQLException {
        Connection connection = null;
        try {
            Class.forName(DBDriver);
            connection = DriverManager.getConnection(DBUrl,userName,passwd);
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into USER values(" + user.getUid() + ",'" +
                user.getUserName() + "','" + user.getMail() + "','" + user.getPwd() + "'," + user.getIsActivated()
                    + ",'" + user.getActivateCode() + "')");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            connection.close();
        }
    }

    public static boolean activateUserByCode(String code) throws SQLException {
        Connection connection = null;
        try {
            Class.forName(DBDriver);
            connection = DriverManager.getConnection(DBUrl,userName,passwd);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from USER where CODE='" + code +"'");
            while (resultSet.next()) {
                // 未激活
                if (resultSet.getInt("ACTIVATION") == 0) {
                    statement.executeUpdate("update USER set ACTIVATION = 1 WHERE CODE = '" + code + "'");
                    return true;
                }
            }
            return false;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally {
            connection.close();
        }
    }

	public static boolean addComment(int userID, int articalID, String content) throws SQLException{
		Connection connection = null;
		try {
			Class.forName(DBDriver);
			connection = DriverManager.getConnection(DBUrl, userName, passwd);
			Date dNow = new Date();
			SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			ft.setTimeZone(TimeZone.getTimeZone("Etc/Greenwich"));
			Statement statement = connection.createStatement();
			String sql = "insert into Comment values ( "+userID+", "+articalID+", '"+content+"', '"+ft.format(dNow)+"')";
			statement.executeUpdate(sql);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally {
			connection.close();
		}
	}

	public static List<Comment> getCommentsByID(int id) throws SQLException {
		ArrayList<Comment> results = new ArrayList<>();
		Connection connection = null;
		try {
			Class.forName(DBDriver);
			connection = DriverManager.getConnection(DBUrl,userName,passwd);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * from Comment where NID="+id+" order by CTIME desc" );
			while (resultSet.next()) {
				Comment comment = new Comment(resultSet.getInt("UID"), resultSet.getInt("NID"), resultSet.getString("CONTENT"));
				comment.setDate(resultSet.getTimestamp("CTIME"));
				results.add(comment);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			connection.close();
		}
		return results;
	}
}
