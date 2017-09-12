package by.htp.newsagent.service.news.validator;

import by.htp.newsagent.domain.news.News;

public interface NewsValidatorService {
	
	boolean isNewsValid(News news);
}
