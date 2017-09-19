package by.htp.newsagent.service.news.validator;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import by.htp.newsagent.domain.news.News;
import by.htp.newsagent.domain.news.NewsStatus;

@RunWith(value = Parameterized.class)
public class NewsValidatorServiceImplTest {
	private static final int TITLE_MAX_LENGTH = 100;
	private static final int TITLE_MIN_LENGTH = 5;
	private static final int BRIEF_MAX_LENGTH = 500;
	private static final int BRIEF_MIN_LENGTH = 5;
	private static final int CONTENT_MAX_LENGTH = 2048;
	private static final int CONTENT_MIN_LENGTH = 5;
	private static final String MIN_NEWS_DATE = "2017-01-01";
	private static final String MAX_NEWS_DATE = "2100-12-31";

	private static Calendar minNewsDate = new GregorianCalendar(2017, 0, 1);
	private static Calendar maxNewsDate = new GregorianCalendar(2100, 11, 31);

	private NewsValidatorServiceImpl newsValidator;

	private int titleLength;
	private int briefLength;
	private int contentLength;
	private NewsStatus newsStatus;
	private Date newsDate;
	private boolean expectedResult;

	Calendar calendar = new GregorianCalendar();

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
		List<Object[]> parameters = new ArrayList<Object[]>(Arrays.asList(new Object[][] {
				{ TITLE_MIN_LENGTH, BRIEF_MIN_LENGTH, CONTENT_MIN_LENGTH, NewsStatus.ACTUAL, minNewsDate.getTime(),
						true },
				{ TITLE_MIN_LENGTH + TITLE_MAX_LENGTH / 2, BRIEF_MIN_LENGTH + BRIEF_MAX_LENGTH / 2,
						CONTENT_MIN_LENGTH + CONTENT_MAX_LENGTH / 2, NewsStatus.ACTUAL, minNewsDate.getTime(), true },
				{ TITLE_MIN_LENGTH - 1, BRIEF_MIN_LENGTH, CONTENT_MIN_LENGTH, NewsStatus.ACTUAL, minNewsDate.getTime(),
						false },
				{ TITLE_MIN_LENGTH, BRIEF_MIN_LENGTH - 1, CONTENT_MIN_LENGTH, NewsStatus.ACTUAL, minNewsDate.getTime(),
						false },
				{ TITLE_MIN_LENGTH, BRIEF_MIN_LENGTH, CONTENT_MIN_LENGTH - 1, NewsStatus.ACTUAL, minNewsDate.getTime(),
						false },

				{ TITLE_MAX_LENGTH, BRIEF_MAX_LENGTH, CONTENT_MAX_LENGTH, NewsStatus.ACTUAL, minNewsDate.getTime(),
						true },
				{ TITLE_MAX_LENGTH + 1, BRIEF_MAX_LENGTH, CONTENT_MAX_LENGTH, NewsStatus.ACTUAL, minNewsDate.getTime(),
						false },
				{ TITLE_MAX_LENGTH, BRIEF_MAX_LENGTH + 1, CONTENT_MAX_LENGTH, NewsStatus.ACTUAL, minNewsDate.getTime(),
						false },
				{ TITLE_MAX_LENGTH, BRIEF_MAX_LENGTH, CONTENT_MAX_LENGTH + 1, NewsStatus.ACTUAL, minNewsDate.getTime(),
						false },

				{ TITLE_MIN_LENGTH, BRIEF_MIN_LENGTH, CONTENT_MIN_LENGTH, null, minNewsDate.getTime(), false },
				{ TITLE_MIN_LENGTH, BRIEF_MIN_LENGTH, CONTENT_MIN_LENGTH, NewsStatus.ACTUAL, null, false },

				{ 0, BRIEF_MIN_LENGTH, CONTENT_MIN_LENGTH, NewsStatus.ACTUAL, minNewsDate.getTime(), false },
				{ TITLE_MIN_LENGTH, 0, CONTENT_MIN_LENGTH, NewsStatus.ACTUAL, minNewsDate.getTime(), false },
				{ TITLE_MIN_LENGTH, BRIEF_MIN_LENGTH, 0, NewsStatus.ACTUAL, minNewsDate.getTime(), false },
				{ TITLE_MIN_LENGTH, BRIEF_MIN_LENGTH, CONTENT_MIN_LENGTH, NewsStatus.ACTUAL, null, false } }));

		minNewsDate.add(Calendar.DAY_OF_MONTH, -1);
		maxNewsDate.add(Calendar.DAY_OF_MONTH, 1);

		parameters.add(new Object[] { TITLE_MIN_LENGTH, BRIEF_MIN_LENGTH, CONTENT_MIN_LENGTH, NewsStatus.ACTUAL,
				minNewsDate.getTime(), false });
		parameters.add(new Object[] { TITLE_MIN_LENGTH, BRIEF_MIN_LENGTH, CONTENT_MIN_LENGTH, NewsStatus.ACTUAL,
				maxNewsDate.getTime(), false });

		minNewsDate.add(Calendar.DAY_OF_MONTH, -90);
		maxNewsDate.add(Calendar.DAY_OF_MONTH, 90);

		parameters.add(new Object[] { TITLE_MIN_LENGTH, BRIEF_MIN_LENGTH, CONTENT_MIN_LENGTH, NewsStatus.ACTUAL,
				minNewsDate.getTime(), false });
		parameters.add(new Object[] { TITLE_MIN_LENGTH, BRIEF_MIN_LENGTH, CONTENT_MIN_LENGTH, NewsStatus.ACTUAL,
				maxNewsDate.getTime(), false });
		parameters.add(new Object[] { TITLE_MIN_LENGTH, BRIEF_MIN_LENGTH, CONTENT_MIN_LENGTH, NewsStatus.ACTUAL,
				new Date(), true });

		return parameters;
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
		newsValidator.setMinNewsDate(new SimpleDateFormat("yyyy-MM-dd").parse(MIN_NEWS_DATE));
		newsValidator.setMaxNewsDate(new SimpleDateFormat("yyyy-MM-dd").parse(MAX_NEWS_DATE));
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
