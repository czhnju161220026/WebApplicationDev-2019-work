package cn.edu.nju.web.util;

import java.util.Date;

public class Article {
	private String header;
	private String content;
	private Date time;
	private String upstream;
	private Integer id;
	private String brefIntroduce;
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

	public String getBrefIntroduce() {
		return brefIntroduce;
	}

	public void setBrefIntroduce(String brefIntroduce) {
		this.brefIntroduce = brefIntroduce;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

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
		this.brefIntroduce = content.substring(0,50)+"...";
	}

	public Article(String header, String content) {
		this.header = header;
		this.content = content;
		this.brefIntroduce = content.substring(0,50)+"...";
	}
	public Article() {}

	@Override
	public String toString() {
		return header + "\n" + content + "\n" + upstream + "\n" + time.toString();
	}
}
