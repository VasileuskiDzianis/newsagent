package by.htp.newsagent.service.news;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.htp.newsagent.dao.news.NewsDao;
import by.htp.newsagent.domain.news.News;
import by.htp.newsagent.domain.news.NewsStatus;
import by.htp.newsagent.service.exception.NewsValidationException;
import by.htp.newsagent.service.news.validator.NewsValidatorService;

@Service
public class NewsServiceImpl implements NewsService {
	@Autowired
	NewsDao newsDao;
	
	@Autowired
	NewsValidatorService newsValidator;

	@Transactional(readOnly = true)
	@Override
	public News findById(int id) {
				
		return newsDao.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<News> findByStatus(NewsStatus status) {
		if (status == null) {
			throw new IllegalArgumentException("Argument can't be null"); 
		}
		return newsDao.findByStatus(status);
	}

	@Transactional
	@Override
	public void archiveOnePieceOfNews(News news) {
		if (news == null) {
			throw new IllegalArgumentException("Argument can't be null"); 
		}
		if (!newsValidator.isNewsValid(news)) {
			throw new NewsValidationException("News: not valid fields");
		}
		news.setStatus(NewsStatus.ARCHIVED);
		newsDao.saveNews(news);
	}

	@Transactional
	@Override
	public void archiveSeveralNews(List<News> news) {
		if (news == null) {
			throw new IllegalArgumentException("Argument can't be null"); 
		}
		for (News newsItem : news) {
			archiveOnePieceOfNews(newsItem);
		}
	}

	@Transactional
	@Override
	public void saveNews(News news) {
		if (news == null) {
			throw new IllegalArgumentException("Argument can't be null"); 
		}
		if (!newsValidator.isNewsValid(news)) {
			throw new NewsValidationException("News: not valid fields");
		}
		newsDao.saveNews(news);
	}
}
