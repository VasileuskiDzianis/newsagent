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

import by.htp.newsagent.domain.news.News;
import by.htp.newsagent.domain.news.NewsStatus;
import by.htp.newsagent.model.Location;
import by.htp.newsagent.model.LocationWebModel;
import by.htp.newsagent.model.NewsItemWebModel;
import by.htp.newsagent.service.news.NewsService;
import by.htp.newsagent.service.validation.variables.PathValidator;

@Controller
@RequestMapping(value = "/news")
public class NewsController {
	private static final int GENUINE_NEWS_ID = 0;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private NewsService newsService;
	
	@Autowired
	StringTrimmerEditor stringTrimmerEditor;
	
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
			NewsItemWebModel newsItemModel = new NewsItemWebModel();
			newsItemModel.setId(newsItem.getId());
			newsItemModel.setTitle(newsItem.getTitle());
			newsItemModel.setBrief(newsItem.getBrief());
			newsItemModel.setNewsDate(newsItem.getNewsDate());
			newsItemModel.setContent(newsItem.getContent());
			newsModelList.add(newsItemModel);
		}
		locationModel.setCurrentLocation(Location.NEWS_LIST);
		locationModel.setPreviousLocation(Location.NEWS);

		model.addAttribute("locationModel", locationModel);
		model.addAttribute("newsList", newsModelList);

		return "news.list";
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public String deleteNews(Model model, HttpServletRequest request,
			@RequestParam(name = "selectedNewsItems", required = false) int[] newsForDeletionIds) {
		if (newsForDeletionIds != null) {
			List<News> newsForDeletion = new ArrayList<>();
			for (int id : newsForDeletionIds) {
				if (id != GENUINE_NEWS_ID) {
					newsForDeletion.add(newsService.findById(id));
				}
			}
			newsService.archiveSeveralNews(newsForDeletion);
		}
		return "redirect:/news";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String saveNews(Model model, @Valid @ModelAttribute("newsItemModel") NewsItemWebModel newsItemModel,
			BindingResult bindingResult, HttpServletRequest request) {
		LocationWebModel locationModel = new LocationWebModel();
		
		if (bindingResult.hasErrors()) {
			if (newsItemModel.getId() > GENUINE_NEWS_ID) {
				locationModel.setCurrentLocation(Location.NEWS_EDIT);
			} else {
				locationModel.setCurrentLocation(Location.NEWS_ADD);
			}
			locationModel.setPreviousLocation(Location.NEWS);
			model.addAttribute("locationModel", locationModel);

			return "news.add";
		}
		News newsItem = new News();
		newsItem.setId(newsItemModel.getId());
		newsItem.setTitle(newsItemModel.getTitle());
		newsItem.setNewsDate(newsItemModel.getNewsDate());
		newsItem.setBrief(newsItemModel.getBrief());
		newsItem.setContent(newsItemModel.getContent());
		newsItem.setStatus(NewsStatus.ACTUAL);

		newsService.saveNews(newsItem);

		return "redirect:/news";
	}

	@RequestMapping(path = "/{rawNewsId}", method = RequestMethod.GET)
	public String getNewsItem(Model model, @PathVariable String rawNewsId, Locale locale, HttpServletRequest request) {
		LocationWebModel locationModel = new LocationWebModel();
		int newsId = 0;

		if (PathValidator.isPathVariableValid(rawNewsId)) {
			newsId = Integer.parseInt(rawNewsId);
		} else {
			locationModel.setCurrentLocation(Location.ERROR);
			locationModel.setPreviousLocation(Location.NEWS);

			model.addAttribute("locationModel", locationModel);
			model.addAttribute("errorMessage", messageSource.getMessage("message.BadRequest400", null, locale));

			return "error";
		}
		News newsItem = newsService.findById(newsId);
		if (newsItem != null) {
			NewsItemWebModel newsItemModel = new NewsItemWebModel();
			newsItemModel.setId(newsItem.getId());
			newsItemModel.setTitle(newsItem.getTitle());
			newsItemModel.setBrief(newsItem.getBrief());
			newsItemModel.setNewsDate(newsItem.getNewsDate());
			newsItemModel.setContent(newsItem.getContent());

			locationModel.setCurrentLocation(Location.NEWS_VIEW);
			locationModel.setPreviousLocation(Location.NEWS);

			model.addAttribute("locationModel", locationModel);
			model.addAttribute("newsItemModel", newsItemModel);

			return "news.view";
		} else {

			return "redirect:/news";
		}
	}

	@RequestMapping(path = "/{rawNewsId}/edit", method = RequestMethod.GET)
	public String editNewsItem(Model model, @PathVariable String rawNewsId, Locale locale, HttpServletRequest request) {
		LocationWebModel locationModel = new LocationWebModel();
		
		if (!PathValidator.isPathVariableValid(rawNewsId)) {
			locationModel.setCurrentLocation(Location.ERROR);
			locationModel.setPreviousLocation(Location.NEWS);

			model.addAttribute("locationModel", locationModel);
			model.addAttribute("errorMessage", messageSource.getMessage("message.BadRequest400", null, locale));

			return "error";
		}

		News newsItem = newsService.findById(Integer.parseInt(rawNewsId));
		NewsItemWebModel newsItemModel = new NewsItemWebModel();

		if (newsItem != null) {
			newsItemModel.setId(newsItem.getId());
			newsItemModel.setTitle(newsItem.getTitle());
			newsItemModel.setBrief(newsItem.getBrief());
			newsItemModel.setNewsDate(newsItem.getNewsDate());
			newsItemModel.setContent(newsItem.getContent());
			
			locationModel.setCurrentLocation(Location.NEWS_EDIT);
		} else {
			newsItemModel.setNewsDate(new Date());
			locationModel.setCurrentLocation(Location.NEWS_ADD);
		}
		locationModel.setPreviousLocation(Location.NEWS);

		model.addAttribute("locationModel", locationModel);
		model.addAttribute("newsItemModel", newsItemModel);

		return "news.add";
	}
}
