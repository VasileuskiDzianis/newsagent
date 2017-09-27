package by.htp.newsagent.domain.news;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class NewsTest {
	private static final int GIVEN_NEWS_ID = 9;
	private static final String GIVEN_NEWS_TITLE = "Short title 9";
	private static final String GIVEN_NEWS_BRIEF = "Short brief 9";
	private static final String GIVEN_NEWS_CONTENT = "Short content 9";
	private static final String DIFFERENT_NEWS_CONTENT = "Short content 10";
	private static final Date GIVEN_NEWS_DATE = new Date();
	private static final NewsStatus GIVEN_NEWS_STATUS = NewsStatus.ACTUAL;
	
	private News givenNewsOne;
	private News givenNewsTwo;
	private News givenNewsThree;

	@Before
	public void setUp() {
		givenNewsOne = new News();
		givenNewsOne.setId(GIVEN_NEWS_ID);
		givenNewsOne.setTitle(GIVEN_NEWS_TITLE);
		givenNewsOne.setBrief(GIVEN_NEWS_BRIEF);
		givenNewsOne.setContent(GIVEN_NEWS_CONTENT);
		givenNewsOne.setNewsDate(GIVEN_NEWS_DATE);
		givenNewsOne.setStatus(GIVEN_NEWS_STATUS);
		
		givenNewsTwo = new News();
		givenNewsTwo.setId(GIVEN_NEWS_ID);
		givenNewsTwo.setTitle(GIVEN_NEWS_TITLE);
		givenNewsTwo.setBrief(GIVEN_NEWS_BRIEF);
		givenNewsTwo.setContent(GIVEN_NEWS_CONTENT);
		givenNewsTwo.setNewsDate(GIVEN_NEWS_DATE);
		givenNewsTwo.setStatus(GIVEN_NEWS_STATUS);
		
		givenNewsThree = new News();
		givenNewsThree.setId(GIVEN_NEWS_ID);
		givenNewsThree.setTitle(GIVEN_NEWS_TITLE);
		givenNewsThree.setBrief(GIVEN_NEWS_BRIEF);
		givenNewsThree.setContent(DIFFERENT_NEWS_CONTENT);
		givenNewsThree.setNewsDate(GIVEN_NEWS_DATE);
		givenNewsThree.setStatus(GIVEN_NEWS_STATUS);
		
	}

	@Test
	public void testEqualsObject() {
		assertTrue(givenNewsOne.equals(givenNewsTwo) && givenNewsTwo.equals(givenNewsOne));
		assertTrue(givenNewsOne.hashCode() == givenNewsTwo.hashCode());
		assertFalse(givenNewsOne.equals(givenNewsThree));
		assertFalse(givenNewsOne.hashCode() == givenNewsThree.hashCode());
	}
}
