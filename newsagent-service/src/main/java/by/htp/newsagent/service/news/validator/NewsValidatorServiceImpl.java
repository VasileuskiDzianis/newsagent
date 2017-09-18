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
	
	@Value("#{new java.text.SimpleDateFormat(\"yyyy-MM-dd\").parse(\"${maxNewsDate}\") ?: new java.text.SimpleDateFormat(\"yyyy-MM-dd\").parse('2200-01-01')}")
	private Date maxNewsDate;

	@Override
	public boolean isNewsValid(News news) {
		if (news == null) {
			throw new IllegalArgumentException("Argument can't be null");
		}

		if (news.getTitle() == null || news.getBrief() == null || news.getContent() == null
				|| news.getNewsDate() == null || news.getStatus() == null) {

			return false;
		}

		if (news.getTitle().length() < titleMinLength || news.getTitle().length() > titleMaxLength) {

			return false;
		}

		if (news.getBrief().length() < briefMinLength || news.getBrief().length() > briefMaxLength) {

			return false;
		}

		if (news.getContent().length() < contentMinLength || news.getContent().length() > contentMaxLength) {

			return false;
		}
		
		if (news.getNewsDate().after(maxNewsDate)) {
			
			return false;
		}
		return true;
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
}
