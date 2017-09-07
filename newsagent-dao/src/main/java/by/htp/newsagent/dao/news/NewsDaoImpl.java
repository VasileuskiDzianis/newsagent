package by.htp.newsagent.dao.news;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import by.htp.newsagent.domain.news.News;
import by.htp.newsagent.domain.news.NewsStatus;

@Transactional
@Repository
public class NewsDaoImpl implements NewsDao {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public News findById(int id) {

		return sessionFactory.getCurrentSession().get(News.class, id);
	}

	@Override
	public List<News> findByStatus(NewsStatus stat) {

		return sessionFactory.getCurrentSession().createQuery("from News u where status = :stat order by newsDate desc", News.class)
				.setParameter("stat", stat).list();
	}

	@Override
	public void saveNews(News news) {
		sessionFactory.getCurrentSession().saveOrUpdate(news);
	}

	@Override
	public void deleteNews(News news) {

		sessionFactory.getCurrentSession().delete(news);
	}
}
