package by.htp.newsagent.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import by.htp.newsagent.controller.form.Location;
import by.htp.newsagent.controller.form.LocationWebModel;
import by.htp.newsagent.controller.form.NewsItemWebModel;
import by.htp.newsagent.domain.news.News;
import by.htp.newsagent.domain.news.NewsStatus;
import by.htp.newsagent.service.news.NewsService;
import by.htp.newsagent.validator.path.PathValidator;

@Controller
@RequestMapping(value = "/news")
public class NewsController {
	private static final int GENUINE_NEWS_ID = 0;

	private static final String PAGE_ERROR_ALIAS = "error";
	private static final String PAGE_NEWS_LIST_REDIRECTION_ALIAS = "redirect:/news";
	private static final String PAGE_NEWS_ADD_ALIAS = "news.add";
	private static final String PAGE_NEWS_VIEW_ALIAS = "news.view";
	private static final String PAGE_NEWS_LIST_ALIAS = "news.list";

	private static final String I18N_400_BAD_REQUEST = "messages.message.BadRequest400";

	private static final String MODEL_ERR_MESSAGE_ALIAS = "errorMessage";
	private static final String MODEL_NEWS_LIST_ALIAS = "newsList";

	private static final String REQ_PARAM_DELETING_NEWS = "selectedNewsItems";

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private NewsService newsService;

	@Autowired
	private StringTrimmerEditor stringTrimmerEditor;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getNewsList(Model model, HttpServletRequest request) {
		LocationWebModel locationModel = new LocationWebModel();
		List<News> newsList = newsService.findByStatus(NewsStatus.ACTUAL);
		List<NewsItemWebModel> newsModelList = new ArrayList<>();
		for (News newsItem : newsList) {
			NewsItemWebModel newsItemModel = buildNewsModelFromNews(newsItem);
			newsModelList.add(newsItemModel);
		}
		locationModel.setCurrentLocation(Location.NEWS_LIST);
		locationModel.setPreviousLocation(Location.NEWS);
		model.addAttribute(LocationWebModel.ALIAS, locationModel);
		model.addAttribute(MODEL_NEWS_LIST_ALIAS, newsModelList);

		return PAGE_NEWS_LIST_ALIAS;
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public String deleteNews(Model model, HttpServletRequest request,
			@RequestParam(name = REQ_PARAM_DELETING_NEWS, required = false) int[] newsForDeletionIds) {
		if (newsForDeletionIds != null) {
			newsService.archiveSeveralNews(newsForDeletionIds);
		}
		return PAGE_NEWS_LIST_REDIRECTION_ALIAS;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String saveNews(Model model, @Valid @ModelAttribute(NewsItemWebModel.ALIAS) NewsItemWebModel newsItemModel,
			BindingResult bindingResult, HttpServletRequest request) {
		LocationWebModel locationModel = new LocationWebModel();

		if (bindingResult.hasErrors()) {
			if (newsItemModel.getId() > GENUINE_NEWS_ID) {
				locationModel.setCurrentLocation(Location.NEWS_EDIT);
			} else {
				locationModel.setCurrentLocation(Location.NEWS_ADD);
			}
			locationModel.setPreviousLocation(Location.NEWS);
			model.addAttribute(LocationWebModel.ALIAS, locationModel);

			return PAGE_NEWS_ADD_ALIAS;
		}
		News newsItem = buildNewsFromModel(newsItemModel);
		newsService.saveNews(newsItem);

		return PAGE_NEWS_LIST_REDIRECTION_ALIAS;
	}

	@RequestMapping(path = "/{rawNewsId}", method = RequestMethod.GET)
	public String getNewsItem(Model model, @PathVariable String rawNewsId, Locale locale, HttpServletRequest request) {
		LocationWebModel locationModel = new LocationWebModel();
		int newsId = GENUINE_NEWS_ID;

		if (PathValidator.isPathVariableValid(rawNewsId)) {
			newsId = Integer.parseInt(rawNewsId);
		} else {
			locationModel.setCurrentLocation(Location.ERROR);
			locationModel.setPreviousLocation(Location.NEWS);
			model.addAttribute(LocationWebModel.ALIAS, locationModel);
			model.addAttribute(MODEL_ERR_MESSAGE_ALIAS, messageSource.getMessage(I18N_400_BAD_REQUEST, null, locale));

			return PAGE_ERROR_ALIAS;
		}
		News newsItem = newsService.findById(newsId);
		if (newsItem != null) {
			NewsItemWebModel newsItemModel = buildNewsModelFromNews(newsItem);
			locationModel.setCurrentLocation(Location.NEWS_VIEW);
			locationModel.setPreviousLocation(Location.NEWS);
			model.addAttribute(LocationWebModel.ALIAS, locationModel);
			model.addAttribute(NewsItemWebModel.ALIAS, newsItemModel);

			return PAGE_NEWS_VIEW_ALIAS;
		} else {

			return PAGE_NEWS_LIST_REDIRECTION_ALIAS;
		}
	}

	@RequestMapping(path = "/{rawNewsId}/edit", method = RequestMethod.GET)
	public String editNewsItem(Model model, @PathVariable String rawNewsId, Locale locale, HttpServletRequest request) {
		LocationWebModel locationModel = new LocationWebModel();

		if (!PathValidator.isPathVariableValid(rawNewsId)) {
			locationModel.setCurrentLocation(Location.ERROR);
			locationModel.setPreviousLocation(Location.NEWS);
			model.addAttribute(LocationWebModel.ALIAS, locationModel);
			model.addAttribute(MODEL_ERR_MESSAGE_ALIAS, messageSource.getMessage(I18N_400_BAD_REQUEST, null, locale));

			return PAGE_ERROR_ALIAS;
		}
		News newsItem = newsService.findById(Integer.parseInt(rawNewsId));
		NewsItemWebModel newsItemModel;

		if (newsItem != null) {
			newsItemModel = buildNewsModelFromNews(newsItem);
			locationModel.setCurrentLocation(Location.NEWS_EDIT);
		} else {
			newsItemModel = new NewsItemWebModel();
			newsItemModel.setNewsDate(new Date());
			locationModel.setCurrentLocation(Location.NEWS_ADD);
		}
		locationModel.setPreviousLocation(Location.NEWS);
		model.addAttribute(LocationWebModel.ALIAS, locationModel);
		model.addAttribute(NewsItemWebModel.ALIAS, newsItemModel);

		return PAGE_NEWS_ADD_ALIAS;
	}

	private News buildNewsFromModel(NewsItemWebModel model) {
		News news = new News();
		news.setId(model.getId());
		news.setTitle(model.getTitle());
		news.setNewsDate(model.getNewsDate());
		news.setBrief(model.getBrief());
		news.setContent(model.getContent());
		news.setStatus(NewsStatus.ACTUAL);

		return news;
	}

	private NewsItemWebModel buildNewsModelFromNews(News news) {
		NewsItemWebModel model = new NewsItemWebModel();
		model.setId(news.getId());
		model.setTitle(news.getTitle());
		model.setBrief(news.getBrief());
		model.setNewsDate(news.getNewsDate());
		model.setContent(news.getContent());

		return model;
	}
}
