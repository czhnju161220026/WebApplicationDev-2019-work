package cn.edu.nju.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Article {
	private String header;
	private String content;
	private Date time;
	private String upstream;
	private Integer id;
	private String briefIntroduce;
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBriefIntroduce() {
		return briefIntroduce;
	}

	public void setBriefIntroduce(String briefIntroduce) {
		this.briefIntroduce = briefIntroduce;
	}

	public String getTime() {
		return "[" + new SimpleDateFormat("yyyy年MM月dd日").format(this.time) + "]";
	}

	public void setTime(Date time) { this.time = time; }

	public String getUpstream() {
		return upstream;
	}

	public void setUpstream(String upstream) {
		this.upstream = upstream;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		this.briefIntroduce = content.substring(0,50 > content.length() ? content.length() - 1 : 50)+"...";
	}

	public Article(String header, String content) {
		this.header = header;
		this.content = content;
		this.briefIntroduce = content.substring(0,50)+"...";
	}
	public Article() {}

	@Override
	public String toString() {
		return header + "\n" + content + "\n" + upstream + "\n" + time.toString();
	}
}
