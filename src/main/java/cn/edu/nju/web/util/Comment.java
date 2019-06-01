package cn.edu.nju.web.util;

import cn.edu.nju.web.database.DatabaseServer;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
	private int userID;
	private int articalID;
	private String userName;
	private String content;
	private Date date;

	public Comment(int userID, int articalID, String content) {
		this.userID = userID;
		this.articalID = articalID;
		this.content = content;
		this.date = new Date();
		try {
			this.userName = DatabaseServer.getUserNameByID(userID);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getUserID() {
		return this.userID;
	}

	public String getContent() {
		return this.content;
	}

	public String getDate() {
		return new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(this.date);
	}

	public String getUserName() {
		return userName;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
