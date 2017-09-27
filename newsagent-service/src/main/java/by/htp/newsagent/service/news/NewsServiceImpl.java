package by.htp.newsagent.service.news;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.htp.newsagent.dao.news.NewsDao;
import by.htp.newsagent.domain.news.News;
import by.htp.newsagent.domain.news.NewsStatus;
import by.htp.newsagent.service.exception.NewsValidationException;
import by.htp.newsagent.service.news.validator.NewsValidatorService;

@Service
public class NewsServiceImpl implements NewsService {
	@Autowired
	private NewsDao newsDao;

	@Autowired
	private NewsValidatorService newsValidator;

	@Override
	public News findById(int id) {

		return newsDao.findById(id);
	}

	@Override
	public List<News> findByStatus(NewsStatus status) {
		if (status == null) {
			throw new IllegalArgumentException("Argument can't be null");
		}
		return newsDao.findByStatus(status);
	}

	@Override
	public void archiveOnePieceOfNews(int id) {
		newsDao.changeNewsStatus(id, NewsStatus.ARCHIVED);
	}

	@Override
	public void archiveSeveralNews(int[] news) {
		if (news == null) {
			throw new IllegalArgumentException("Argument can't be null");
		}
		for (int id : news) {
			archiveOnePieceOfNews(id);
		}
	}

	@Override
	public void saveNews(News news) {
		if (news == null) {
			throw new IllegalArgumentException("Argument can't be null");
		}
		if (!newsValidator.isNewsValid(news)) {
			throw new NewsValidationException("News: are not valid fields");
		}
		newsDao.saveNews(news);
	}
}
