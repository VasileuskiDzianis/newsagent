package by.htp.newsagent.controller;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import by.htp.newsagent.domain.news.News;
import by.htp.newsagent.domain.news.NewsStatus;
import by.htp.newsagent.model.Location;
import by.htp.newsagent.model.LocationWebModel;
import by.htp.newsagent.model.NewsItemWebModel;
import by.htp.newsagent.service.news.NewsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("test-servlet-context.xml")
@WebAppConfiguration
public class NewsControllerTest {
	private static final int NEWS_ID_1 = 66;
	private static final Date NEWS_DATE_1 = new Date();
	private static final String NEWS_TITLE_1 = "Title1";
	private static final String NEWS_BRIEF_1 = "Brief1";
	private static final String NEWS_CONTENT_1 = "Content1";
	private static final NewsStatus NEWS_STATUS_1 = NewsStatus.ACTUAL;

	private static final int NEWS_ID_2 = 88;
	private static final Date NEWS_DATE_2 = new Date();
	private static final String NEWS_TITLE_2 = "Title2";
	private static final String NEWS_BRIEF_2 = "Brief2";
	private static final String NEWS_CONTENT_2 = "Content2";
	private static final NewsStatus NEWS_STATUS_2 = NewsStatus.ACTUAL;
	
	private static final int NEWS_ID_NOT_EXISTING = 99;

	private News mockNews1;
	private News mockNews2;

	@Configuration
	static class Config {
		@Bean
		@Primary
		public NewsService newsServiceMock() {
			
			return Mockito.mock(NewsService.class);
		}
	}

	@Autowired
	private WebApplicationContext wac;

	@Mock
	private NewsService newsService;
	
	private MockMvc mockMvc;

	@InjectMocks
	private NewsController newsController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		this.newsService = (NewsService) this.wac.getBean("newsServiceMock");

		mockNews1 = new News();
		mockNews1.setId(NEWS_ID_1);
		mockNews1.setNewsDate(NEWS_DATE_1);
		mockNews1.setBrief(NEWS_BRIEF_1);
		mockNews1.setTitle(NEWS_TITLE_1);
		mockNews1.setContent(NEWS_CONTENT_1);
		mockNews1.setStatus(NEWS_STATUS_1);

		mockNews2 = new News();
		mockNews2.setId(NEWS_ID_2);
		mockNews2.setNewsDate(NEWS_DATE_2);
		mockNews2.setBrief(NEWS_BRIEF_2);
		mockNews2.setTitle(NEWS_TITLE_2);
		mockNews2.setContent(NEWS_CONTENT_2);
		mockNews2.setStatus(NEWS_STATUS_2);

		when(newsService.findByStatus(NEWS_STATUS_1)).thenReturn(Arrays.asList(mockNews1, mockNews2));
		when(newsService.findById(NEWS_ID_2)).thenReturn(mockNews2);
		when(newsService.findById(NEWS_ID_1)).thenReturn(mockNews1);
		when(newsService.findById(NEWS_ID_NOT_EXISTING)).thenReturn(null);
		
	}

	@Test
	public void getNewsItemWithLegalIdTest() throws Exception {
		this.mockMvc.perform(get("/news/" + NEWS_ID_2))
				.andExpect(status().isOk())
				.andExpect(view().name("news.view"))
				.andExpect(forwardedUrl("/WEB-INF/layouts/newsagent.jsp"))
				.andExpect(model().attribute("locationModel",
						allOf(Matchers.<LocationWebModel>hasProperty("currentLocation", equalTo(Location.NEWS_VIEW)),
								Matchers.<LocationWebModel>hasProperty("previousLocation", equalTo(Location.NEWS)))))
				.andExpect(model().attribute("newsItemModel", 
						allOf(
								Matchers.<NewsItemWebModel>hasProperty("id", equalTo(NEWS_ID_2)),
								Matchers.<NewsItemWebModel>hasProperty("newsDate", equalTo(NEWS_DATE_2)),
								Matchers.<NewsItemWebModel>hasProperty("title", equalTo(NEWS_TITLE_2)),
								Matchers.<NewsItemWebModel>hasProperty("brief", equalTo(NEWS_BRIEF_2)),
								Matchers.<NewsItemWebModel>hasProperty("content", equalTo(NEWS_CONTENT_2))
								)));
	}
	
	@Test
	public void getNewsItemWithBadFormatIdTest() throws Exception {
		this.mockMvc.perform(get("/news/bad"))
		.andExpect(status().isOk())
		.andExpect(view().name("error"))
		.andExpect(forwardedUrl("/WEB-INF/layouts/newsagent.jsp"))
		.andExpect(model().attribute("locationModel",
				allOf(Matchers.<LocationWebModel>hasProperty("currentLocation", equalTo(Location.ERROR)),
						Matchers.<LocationWebModel>hasProperty("previousLocation", equalTo(Location.NEWS)))))
		.andExpect(model().attribute("errorMessage", 
				anyOf(
						equalTo("Код 400 Не верный запрос"),
						equalTo("Code 400 Bad Request")
						)));
	}
	
	@Test
	public void getNewsItemWithNotExistingIdTest() throws Exception {
		this.mockMvc.perform(get("/news/" + NEWS_ID_NOT_EXISTING))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/news"));
	}

	@Test
	public void deleteNewsTest() throws Exception {
		this.mockMvc.perform(delete("/news").param("selectedNewsItems", "66", "88"))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/news"));
	}

	@Test
	public void saveNewsValidDataModel() throws Exception {
		this.mockMvc.perform(post("/news")
				.param("id", "0")
				.param("title", "LegalTitle")
				.param("brief", "LegalBrief")
				.param("cintent", "LegalContent")
				.param("newsDate", "13/09/2017"))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/news"));
	}
	
	//BindingResult doesn't find errors in any cases with hibernate-validator 5.4.1.Final
	//particularly works with hibernate-validator 4.1.0.Final and javax.validation validation-api 1.0.0.GA
	@Ignore 
	@Test
	public void saveGenuineNewsNotValidDataModel() throws Exception {
		this.mockMvc.perform(post("/news")
				.param("id", "0")
				.param("title", "bad")
				.param("brief", "LegalBrief")
				.param("content", "LegalContent")
				.param("newsDate", "13/09/20"))
		.andExpect(status().isOk())
		.andExpect(view().name("news.add"))
		.andExpect(forwardedUrl("/WEB-INF/layouts/newsagent.jsp"))
		.andExpect(model().attribute("locationModel",
				allOf(
						Matchers.<LocationWebModel>hasProperty("currentLocation", equalTo(Location.NEWS_ADD)),
						Matchers.<LocationWebModel>hasProperty("previousLocation", equalTo(Location.NEWS)))));
	}

	@Test
	public void testGetNewsList() throws Exception {
		this.mockMvc.perform(get("/news"))
		.andExpect(status().isOk())
		.andExpect(view().name("news.list"))
		.andExpect(forwardedUrl("/WEB-INF/layouts/newsagent.jsp"))
		.andExpect(model().attribute("locationModel",
				allOf(
						Matchers.<LocationWebModel>hasProperty("currentLocation", equalTo(Location.NEWS_LIST)),
						Matchers.<LocationWebModel>hasProperty("previousLocation", equalTo(Location.NEWS)))))
		.andExpect(model().attribute("newsList", Matchers.<List<NewsItemWebModel>>hasSize(2)))
		.andExpect(model().attribute("newsList", hasItem(
				allOf(
						Matchers.<NewsItemWebModel>hasProperty("id", equalTo(NEWS_ID_1)),
						Matchers.<NewsItemWebModel>hasProperty("newsDate", equalTo(NEWS_DATE_1)),
						Matchers.<NewsItemWebModel>hasProperty("title", equalTo(NEWS_TITLE_1)),
						Matchers.<NewsItemWebModel>hasProperty("brief", equalTo(NEWS_BRIEF_1)),
						Matchers.<NewsItemWebModel>hasProperty("content", equalTo(NEWS_CONTENT_1))
						))))
		.andExpect(model().attribute("newsList", hasItem(
				allOf(
						Matchers.<NewsItemWebModel>hasProperty("id", equalTo(NEWS_ID_2)),
						Matchers.<NewsItemWebModel>hasProperty("newsDate", equalTo(NEWS_DATE_2)),
						Matchers.<NewsItemWebModel>hasProperty("title", equalTo(NEWS_TITLE_2)),
						Matchers.<NewsItemWebModel>hasProperty("brief", equalTo(NEWS_BRIEF_2)),
						Matchers.<NewsItemWebModel>hasProperty("content", equalTo(NEWS_CONTENT_2))
						))));
	}

	@Test
	public void editNewsItemBadFormatIdTest() throws Exception {
		this.mockMvc.perform(get("/news/1q/edit"))
		.andExpect(status().isOk())
		.andExpect(view().name("error"))
		.andExpect(forwardedUrl("/WEB-INF/layouts/newsagent.jsp"))
		.andExpect(model().attribute("locationModel",
				allOf(Matchers.<LocationWebModel>hasProperty("currentLocation", equalTo(Location.ERROR)),
						Matchers.<LocationWebModel>hasProperty("previousLocation", equalTo(Location.NEWS)))))
		.andExpect(model().attribute("errorMessage", 
				anyOf(
						equalTo("Код 400 Не верный запрос"),
						equalTo("Code 400 Bad Request")
						)));
	}
	
	@Test
	public void editNewsItemLegalIdTest() throws Exception {
		this.mockMvc.perform(get("/news/" + NEWS_ID_2 + "/edit"))
		.andExpect(status().isOk())
		.andExpect(view().name("news.add"))
		.andExpect(forwardedUrl("/WEB-INF/layouts/newsagent.jsp"))
		.andExpect(model().attribute("locationModel",
				allOf(Matchers.<LocationWebModel>hasProperty("currentLocation", equalTo(Location.NEWS_EDIT)),
						Matchers.<LocationWebModel>hasProperty("previousLocation", equalTo(Location.NEWS)))))
		.andExpect(model().attribute("newsItemModel", 
				allOf(
						Matchers.<NewsItemWebModel>hasProperty("id", equalTo(NEWS_ID_2)),
						Matchers.<NewsItemWebModel>hasProperty("newsDate", equalTo(NEWS_DATE_2)),
						Matchers.<NewsItemWebModel>hasProperty("title", equalTo(NEWS_TITLE_2)),
						Matchers.<NewsItemWebModel>hasProperty("brief", equalTo(NEWS_BRIEF_2)),
						Matchers.<NewsItemWebModel>hasProperty("content", equalTo(NEWS_CONTENT_2))
						)));
	}
	
	@Test
	public void editNewsItemNotExistingIdTest() throws Exception {
		this.mockMvc.perform(get("/news/" + NEWS_ID_NOT_EXISTING + "/edit"))
		.andExpect(status().isOk())
		.andExpect(view().name("news.add"))
		.andExpect(forwardedUrl("/WEB-INF/layouts/newsagent.jsp"))
		.andExpect(model().attribute("locationModel",
				allOf(Matchers.<LocationWebModel>hasProperty("currentLocation", equalTo(Location.NEWS_ADD)),
						Matchers.<LocationWebModel>hasProperty("previousLocation", equalTo(Location.NEWS)))))
		.andExpect(model().attribute("newsItemModel", 
				allOf(
						Matchers.<NewsItemWebModel>hasProperty("id", equalTo(0)),
						Matchers.<NewsItemWebModel>hasProperty("newsDate", notNullValue()),
						Matchers.<NewsItemWebModel>hasProperty("title", nullValue()),
						Matchers.<NewsItemWebModel>hasProperty("brief", nullValue()),
						Matchers.<NewsItemWebModel>hasProperty("content", nullValue())
						)));
	}
	
	@After
	public void validate() {
	  validateMockitoUsage();
	}
}
