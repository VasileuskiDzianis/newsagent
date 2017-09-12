package by.htp.newsagent.dao.news;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import by.htp.newsagent.domain.news.News;
import by.htp.newsagent.domain.news.NewsStatus;

public class NewsDaoImplTest extends NewsDaoImpl {
	private static final int EXPECTED_NEWS_ID = 3;
	private static final String EXPECTED_NEWS_TITLE = "Short title 3";
	private static final String EXPECTED_NEWS_BRIEF = "Short brief 3";
	private static final String EXPECTED_NEWS_CONTENT = "Short content 3";
	private static final NewsStatus EXPECTED_NEWS_STATUS = NewsStatus.ACTUAL;
	private static final List<Integer> ACTUAL_NEWS_IDS = Arrays.asList(1, 2, 3, 5, 6, 7, 8);
	private static final Integer ARCHIVED_NEWS_ID = 4;

	private static final String GIVEN_NEWS_TITLE = "Short title 9";
	private static final String GIVEN_CHANGED_NEWS_TITLE = "Short title 9 changed";
	private static final String GIVEN_NEWS_BRIEF = "Short brief 9";
	private static final String GIVEN_NEWS_CONTENT = "Short content 9";
	private static final NewsStatus GIVEN_NEWS_STATUS = NewsStatus.ACTUAL;
	
	private static final int ILLEGAL_ID = 0;

	private Date expectedNewsDate;
	private NewsDao newsDao;
	private ClassPathXmlApplicationContext context;

	@Before
	public void setUp() throws ParseException {
		context = new ClassPathXmlApplicationContext("test_spring_context.xml");
		newsDao = (NewsDao) context.getBean("newsDaoImpl");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		expectedNewsDate = dateFormat.parse("2017-09-03");
	}

	@Test
	public void findByIdTest(){
		News expectedNews = new News();
		expectedNews.setId(EXPECTED_NEWS_ID);
		expectedNews.setNewsDate(expectedNewsDate);
		expectedNews.setTitle(EXPECTED_NEWS_TITLE);
		expectedNews.setBrief(EXPECTED_NEWS_BRIEF);
		expectedNews.setContent(EXPECTED_NEWS_CONTENT);
		expectedNews.setStatus(EXPECTED_NEWS_STATUS);

		News gotNews = newsDao.findById(EXPECTED_NEWS_ID);

		assertTrue(expectedNews.equals(gotNews));
	}

	@Test
	public void findByStatusTest() {
		List<News> storedNews = newsDao.findByStatus(NewsStatus.ACTUAL);
		List<Integer> storedNewsIds = new ArrayList<>();

		for (News news : storedNews) {
			storedNewsIds.add(news.getId());
		}
		for (Integer actualId : ACTUAL_NEWS_IDS) {
			assertTrue(storedNewsIds.contains(actualId));
		}
		assertFalse(storedNewsIds.contains(ARCHIVED_NEWS_ID));
	}

	@Test
	public void saveUpdateAndDeleteNewsTest() {
		News givenNews = new News();
		givenNews.setTitle(GIVEN_NEWS_TITLE);
		givenNews.setBrief(GIVEN_NEWS_BRIEF);
		givenNews.setContent(GIVEN_NEWS_CONTENT);
		givenNews.setNewsDate(expectedNewsDate);
		givenNews.setStatus(GIVEN_NEWS_STATUS);
		
		newsDao.saveNews(givenNews);
		
		int storedNewsId = givenNews.getId();
		
		assertTrue("id must be greater than 0: ", storedNewsId > ILLEGAL_ID);
		
		News storedNews = newsDao.findById(storedNewsId);
		
		assertTrue("given and stored users must be equals: ", givenNews.equals(storedNews));
		
		givenNews.setTitle(GIVEN_CHANGED_NEWS_TITLE);
		
		newsDao.saveNews(givenNews);
		
		storedNews = newsDao.findById(storedNewsId);
		
		assertEquals(GIVEN_CHANGED_NEWS_TITLE, storedNews.getTitle());
		
		newsDao.deleteNews(storedNews);
		
		assertNull(newsDao.findById(storedNewsId));
	}
	
	@After
	public void closeContext() {
		context.close();
	}
}
