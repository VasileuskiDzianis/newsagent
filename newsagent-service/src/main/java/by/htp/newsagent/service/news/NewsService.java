package by.htp.newsagent.service.news;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import by.htp.newsagent.domain.news.News;
import by.htp.newsagent.domain.news.NewsStatus;

@Transactional
public interface NewsService {
	@Transactional(readOnly = true)
	News findById(int id);

	@Transactional(readOnly = true)
	List<News> findByStatus(NewsStatus status);

	@Transactional
	void saveNews(News news);

	@Transactional
	void archiveOnePieceOfNews(News news);

	@Transactional
	void archiveSeveralNews(List<News> news);
}
