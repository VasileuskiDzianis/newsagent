package by.htp.newsagent.service.news.validator;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import by.htp.newsagent.domain.news.News;
import by.htp.newsagent.domain.news.NewsStatus;

@RunWith(value = Parameterized.class)
public class NewsValidatorServiceImplTest extends NewsValidatorServiceImpl {
	private static final int TITLE_MAX_LENGTH = 100;
	private static final int TITLE_MIN_LENGTH = 5;
	private static final int BRIEF_MAX_LENGTH = 500;
	private static final int BRIEF_MIN_LENGTH = 5;
	private static final int CONTENT_MAX_LENGTH = 2048;
	private static final int CONTENT_MIN_LENGTH = 5;

	private NewsValidatorServiceImpl newsValidator;

	private int titleLength;
	private int briefLength;
	private int contentLength;
	private NewsStatus newsStatus;
	private Date newsDate;
	private boolean expectedResult;

	public NewsValidatorServiceImplTest(int titleLength, int briefLength, int contentLength, NewsStatus newsStatus,
			Date newsDate, boolean expectedResult) {
		this.titleLength = titleLength;
		this.briefLength = briefLength;
		this.contentLength = contentLength;
		this.newsStatus = newsStatus;
		this.newsDate = newsDate;
		this.expectedResult = expectedResult;
	}

	@Parameters
	public static Collection<Object[]> newsBatch() {

		return Arrays.asList(new Object[][] {
				{ TITLE_MIN_LENGTH, BRIEF_MIN_LENGTH, CONTENT_MIN_LENGTH, NewsStatus.ACTUAL, new Date(), true },
				{ TITLE_MIN_LENGTH + TITLE_MAX_LENGTH / 2, BRIEF_MIN_LENGTH + BRIEF_MAX_LENGTH / 2,
						CONTENT_MIN_LENGTH + CONTENT_MAX_LENGTH / 2, NewsStatus.ACTUAL, new Date(), true },
				{ TITLE_MIN_LENGTH - 1, BRIEF_MIN_LENGTH, CONTENT_MIN_LENGTH, NewsStatus.ACTUAL, new Date(), false },
				{ TITLE_MIN_LENGTH, BRIEF_MIN_LENGTH - 1, CONTENT_MIN_LENGTH, NewsStatus.ACTUAL, new Date(), false },
				{ TITLE_MIN_LENGTH, BRIEF_MIN_LENGTH, CONTENT_MIN_LENGTH - 1, NewsStatus.ACTUAL, new Date(), false },

				{ TITLE_MAX_LENGTH, BRIEF_MAX_LENGTH, CONTENT_MAX_LENGTH, NewsStatus.ACTUAL, new Date(), true },
				{ TITLE_MAX_LENGTH + 1, BRIEF_MAX_LENGTH, CONTENT_MAX_LENGTH, NewsStatus.ACTUAL, new Date(), false },
				{ TITLE_MAX_LENGTH, BRIEF_MAX_LENGTH + 1, CONTENT_MAX_LENGTH, NewsStatus.ACTUAL, new Date(), false },
				{ TITLE_MAX_LENGTH, BRIEF_MAX_LENGTH, CONTENT_MAX_LENGTH + 1, NewsStatus.ACTUAL, new Date(), false },

				{ TITLE_MIN_LENGTH, BRIEF_MIN_LENGTH, CONTENT_MIN_LENGTH, null, new Date(), false },
				{ TITLE_MIN_LENGTH, BRIEF_MIN_LENGTH, CONTENT_MIN_LENGTH, NewsStatus.ACTUAL, null, false },
				
				{ 0, BRIEF_MIN_LENGTH, CONTENT_MIN_LENGTH, NewsStatus.ACTUAL, new Date(), false },
				{ TITLE_MIN_LENGTH, 0, CONTENT_MIN_LENGTH, NewsStatus.ACTUAL, new Date(), false },
				{ TITLE_MIN_LENGTH, BRIEF_MIN_LENGTH, 0, NewsStatus.ACTUAL, new Date(), false }
		});
	}

	@Before
	public void setUp() throws Exception {
		newsValidator = new NewsValidatorServiceImpl();
		newsValidator.setTitleMinLength(TITLE_MIN_LENGTH);
		newsValidator.setTitleMaxLength(TITLE_MAX_LENGTH);
		newsValidator.setBriefMinLength(BRIEF_MIN_LENGTH);
		newsValidator.setBriefMaxLength(BRIEF_MAX_LENGTH);
		newsValidator.setContentMinLength(CONTENT_MIN_LENGTH);
		newsValidator.setContentMaxLength(CONTENT_MAX_LENGTH);
	}

	@Test
	public void isNewsValidTest() {
		News generatedNews = generateNews(titleLength, briefLength, contentLength, newsStatus, newsDate);

		assertEquals(expectedResult, newsValidator.isNewsValid(generatedNews));
	}

	private News generateNews(int title, int brief, int content, NewsStatus status, Date newsDate) {
		News news = new News();
		if (title > 0) {
			news.setTitle(generateText(title));
		}
		if (brief > 0) {
			news.setBrief(generateText(brief));
		}
		if (content > 0) {
			news.setContent(generateText(content));
		}
		news.setStatus(status);
		news.setNewsDate(newsDate);

		return news;
	}

	private String generateText(int length) {
		StringBuilder text = new StringBuilder();
		for (int i = 0; i < length; i++) {
			text.append('z');
		}
		return text.toString();
	}
}
