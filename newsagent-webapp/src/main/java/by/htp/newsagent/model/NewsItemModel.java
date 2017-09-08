package by.htp.newsagent.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class NewsItemModel {
	private int id;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "can not be empty")
	private Date newsDate;

	@Size(min = 5, max = 100, message = "5-100 chars")
	private String title;

	@NotNull(message = "can not be empty")
	@Size(min = 5, max = 500, message = "5-500 chars")
	private String brief;

	@NotNull(message = "can not be empty")
	@Size(min = 5, max = 2048, message = "5-2048 chars")
	private String content;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(Date newsDate) {
		this.newsDate = newsDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}