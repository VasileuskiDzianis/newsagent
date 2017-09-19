package by.htp.newsagent.controller;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Date;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import by.htp.newsagent.domain.news.News;
import by.htp.newsagent.domain.news.NewsStatus;
import by.htp.newsagent.model.Location;
import by.htp.newsagent.model.LocationWebModel;
import by.htp.newsagent.service.news.NewsService;

public class NewsControllerAdvancedTest extends NewsController {
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
	
	@Mock
	private NewsService newsService;
	
	@InjectMocks
	private NewsController newsController;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(newsController).build();
		
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
	public void deleteNewsTest() throws Exception {
		this.mockMvc.perform(delete("/news").param("selectedNewsItems", "66", "88"))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/news"));
		
		verify(newsService).archiveSeveralNews(any());
		verify(newsService, times(2)).findById(anyInt());
	}
	
	//BindingResult doesn't have errors with hibernate-validator 5.4.1.Final
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
}
