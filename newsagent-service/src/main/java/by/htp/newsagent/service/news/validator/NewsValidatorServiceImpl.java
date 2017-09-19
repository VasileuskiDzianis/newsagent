package by.htp.newsagent.service.news.validator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import by.htp.newsagent.domain.news.News;

@Service
public class NewsValidatorServiceImpl implements NewsValidatorService {
	@Value("#{${titleMinLength} ?: 5}")
	private int titleMinLength;

	@Value("#{${titleMaxLength} ?: 100}")
	private int titleMaxLength;

	@Value("#{${briefMinLength} ?: 5}")
	private int briefMinLength;

	@Value("#{${briefMaxLength} ?: 500}")
	private int briefMaxLength;

	@Value("#{${contentMinLength} ?: 5}")
	private int contentMinLength;

	@Value("#{${contentMaxLength} ?: 2048}")
	private int contentMaxLength;

	@Value("#{new java.text.SimpleDateFormat(\"yyyy-MM-dd\").parse(\"${maxNewsDate}\") ?: new java.text.SimpleDateFormat(\"yyyy-MM-dd\").parse('2100-12-31')}")
	private Date maxNewsDate;

	@Value("#{new java.text.SimpleDateFormat(\"yyyy-MM-dd\").parse(\"${minNewsDate}\") ?: new java.text.SimpleDateFormat(\"yyyy-MM-dd\").parse('2017-01-01')}")
	private Date minNewsDate;

	@Override
	public boolean isNewsValid(News news) {
		if (news == null) {
			throw new IllegalArgumentException("Argument can't be null");
		}
		return !(hasNullFields(news) 
				|| isLengthNotInRange(news.getTitle(), titleMinLength, titleMaxLength) 
				|| isLengthNotInRange(news.getBrief(), briefMinLength, briefMaxLength)
				|| isLengthNotInRange(news.getContent(), contentMinLength, contentMaxLength)
				|| isDateNotInRange(news.getNewsDate(), minNewsDate, maxNewsDate));
	}
	
	private boolean hasNullFields (News news) {
		
		return (news.getTitle() == null 
				|| news.getBrief() == null 
				|| news.getContent() == null
				|| news.getNewsDate() == null 
				|| news.getStatus() == null);
	}
	
	private boolean isLengthNotInRange(String text, int min, int max) {
		
		return (text.length() < min || text.length() > max);
	}
	
	private boolean isDateNotInRange(Date date, Date minDate, Date maxDate) {
		
		return (date.before(minDate) || date.after(maxDate));
	}
	
	public void setTitleMinLength(int titleMinLength) {
		this.titleMinLength = titleMinLength;
	}

	public void setTitleMaxLength(int titleMaxLength) {
		this.titleMaxLength = titleMaxLength;
	}

	public void setBriefMinLength(int briefMinLength) {
		this.briefMinLength = briefMinLength;
	}

	public void setBriefMaxLength(int briefMaxLength) {
		this.briefMaxLength = briefMaxLength;
	}

	public void setContentMinLength(int contentMinLength) {
		this.contentMinLength = contentMinLength;
	}

	public void setContentMaxLength(int contentMaxLength) {
		this.contentMaxLength = contentMaxLength;
	}

	public void setMaxNewsDate(Date maxNewsDate) {
		this.maxNewsDate = maxNewsDate;
	}

	public void setMinNewsDate(Date minNewsDate) {
		this.minNewsDate = minNewsDate;
	}
}
