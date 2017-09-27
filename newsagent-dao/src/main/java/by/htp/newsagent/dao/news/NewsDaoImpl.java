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
	private static final String REQ_FIND_BY_ID = "from News u where status = :stat order by newsDate desc";
	private static final String REQ_CHANGE_NEWS_STAT = "update News n set n.status = :stat where n.id = :n_id";
	private static final String REQ_PARAM_STATUS = "stat";
	private static final String REQ_PARAM_NEWS_ID = "n_id";
	
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public News findById(int id) {

		return sessionFactory.getCurrentSession().get(News.class, id);
	}

	@Override
	public List<News> findByStatus(NewsStatus stat) {

		return sessionFactory.getCurrentSession()
				.createQuery(REQ_FIND_BY_ID, News.class)
				.setParameter(REQ_PARAM_STATUS, stat).list();
	}

	@Override
	public void saveNews(News news) {
		sessionFactory.getCurrentSession().saveOrUpdate(news);
	}

	@Override
	public void deleteNews(News news) {
		sessionFactory.getCurrentSession().delete(news);
	}
	
	@Override
	public void changeNewsStatus(int id, NewsStatus stat) {
		sessionFactory.getCurrentSession().createQuery(REQ_CHANGE_NEWS_STAT)
		.setParameter(REQ_PARAM_STATUS, stat)
		.setParameter(REQ_PARAM_NEWS_ID, id)
		.executeUpdate();
	}
}
