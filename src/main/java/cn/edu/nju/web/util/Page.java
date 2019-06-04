package cn.edu.nju.web.util;

import java.util.ArrayList;
import java.util.List;

public class Page {
	public static List<Page> getPages(String category, List<Article> articleList) {
		List<Page> pages = new ArrayList<>();
		int numOfArticles = articleList.size();
		int numOfPages = (int) Math.ceil((double) numOfArticles / 10);
		for (int i = 0; i < numOfPages; i++) {
			Page page = new Page();
			page.setCategory(category);
			page.setIndex(i + 1);
			page.setId("" + (i + 1));
			page.setUrl("/articles?category=" + category + "&page=" + page.index);
			pages.add(page);
		}

		return pages;
	}

	private int index;
	private String id;
	private String category;
	private String url;
	private String user = null;

	public void setUser(String user) {
		this.user = user;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUrl() {
		if(user!=null) {
			url += ("&user=" + user);
		}
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
