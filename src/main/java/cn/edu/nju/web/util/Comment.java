package cn.edu.nju.web.util;

import cn.edu.nju.web.database.DatabaseServer;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
	private int userID;
	private int articalID;
	private int num;
	private int cid;
	private String userName;
	private String content;
	private Date date;
	private boolean thumbUp;

	public Comment(int userID,int cid ,int articalID, String content, int num) {
		this.userID = userID;
		this.cid = cid;
		this.articalID = articalID;
		this.content = content;
		this.date = new Date();
		this.num = num;

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

	public boolean isThumbUp() {
		return thumbUp;
	}

	public void setThumbUp(boolean thumbUp) {
		this.thumbUp = thumbUp;
	}

	public int getNum() {
		return num;
	}

	public int getCid() {
		return cid;
	}
}
