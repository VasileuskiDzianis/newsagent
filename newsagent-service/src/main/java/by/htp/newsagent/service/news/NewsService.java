package by.htp.newsagent.service.news;

import java.util.List;

import by.htp.newsagent.domain.news.News;
import by.htp.newsagent.domain.news.NewsStatus;

public interface NewsService {
	
	News findById(int id);
	
	List<News> findByStatus(NewsStatus status);
	
	void archiveOnePieceOfNews(News news);
	
	void archiveSeveralNews(List<News> news);
}
