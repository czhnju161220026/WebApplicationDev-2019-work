package cn.edu.nju.web.database;

import cn.edu.nju.web.util.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class DatabaseServer {
	private static String DBDriver = "com.mysql.jdbc.Driver";
	private static String DBUrl = "jdbc:mysql://localhost:3306/web_db?useUnicode=true&characterEncoding=utf8";
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
}
