package by.htp.newsagent.dao.news;

import java.util.List;

import by.htp.newsagent.domain.news.News;
import by.htp.newsagent.domain.news.NewsStatus;

public interface NewsDao {

	News findById(int id);

	List<News> findByStatus(NewsStatus status);

	void saveNews(News news);
	
	void deleteNews(News news);

	void changeNewsStatus(int id, NewsStatus stat);
}
