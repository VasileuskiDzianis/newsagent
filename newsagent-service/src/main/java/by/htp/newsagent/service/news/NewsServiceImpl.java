package by.htp.newsagent.service.news;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.htp.newsagent.dao.news.NewsDao;
import by.htp.newsagent.domain.news.News;
import by.htp.newsagent.domain.news.NewsStatus;

@Service
public class NewsServiceImpl implements NewsService {
	@Autowired
	NewsDao newsDao;

	@Transactional(readOnly = true)
	@Override
	public News findById(int id) {

		return newsDao.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<News> findByStatus(NewsStatus status) {

		return newsDao.findByStatus(status);
	}

	@Transactional
	@Override
	public void archiveOnePieceOfNews(News news) {
		news.setStatus(NewsStatus.ARCHIVED);
		newsDao.saveNews(news);
	}

	@Transactional
	@Override
	public void archiveSeveralNews(List<News> news) {
		for (News newsItem : news) {
			archiveOnePieceOfNews(newsItem);
		}
	}
}
