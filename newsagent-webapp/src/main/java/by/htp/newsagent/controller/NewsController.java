package by.htp.newsagent.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import by.htp.newsagent.domain.news.News;
import by.htp.newsagent.domain.news.NewsStatus;
import by.htp.newsagent.model.NewsItemModel;
import by.htp.newsagent.service.news.NewsService;

@Controller
@RequestMapping(value="/news")
public class NewsController {
	
	@Autowired
	private NewsService newsService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getNewsList(Model model) {
		List<News> newsList = newsService.findByStatus(NewsStatus.ACTUAL);
		List<NewsItemModel> newsModelList = new ArrayList<>();
		
		for (News newsItem : newsList) {
			NewsItemModel newsItemModel = new NewsItemModel();
			newsItemModel.setId(newsItem.getId());
			newsItemModel.setTitle(newsItem.getTitle());
			newsItemModel.setBrief(newsItem.getBrief());
			newsItemModel.setNewsDate(newsItem.getNewsDate());
			newsItemModel.setContent(newsItem.getContent());
			newsModelList.add(newsItemModel);
		}
		model.addAttribute("newsList", newsModelList);
		
		return "news.list";
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public String deleteNews(Model model, @RequestParam(name="selectedNewsItems", required=false) int[] newsForDeletionIds) {
		if (newsForDeletionIds != null) {
			List<News> newsForDeletion = new ArrayList<>();
			for (int id : newsForDeletionIds) {
				newsForDeletion.add(newsService.findById(id));
			}
			
			newsService.archiveSeveralNews(newsForDeletion);
		}
		return "redirect:/news";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String saveNews(Model model, @ModelAttribute("newsItemModel")  NewsItemModel newsItemModel, BindingResult bindingResult) {
		
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
	
	@RequestMapping(path = "/{newsId}", method = RequestMethod.GET)
	public String getNewsItem(Model model, @PathVariable int newsId) {
		News newsItem = newsService.findById(newsId);
		NewsItemModel newsItemModel = new NewsItemModel();
		
		newsItemModel.setId(newsItem.getId());
		newsItemModel.setTitle(newsItem.getTitle());
		newsItemModel.setBrief(newsItem.getBrief());
		newsItemModel.setNewsDate(newsItem.getNewsDate());
		newsItemModel.setContent(newsItem.getContent());
		
		model.addAttribute("newsItemModel", newsItemModel);
		
		return "news.view";
	}
	
	@RequestMapping(path = "/{newsId}/edit", method = RequestMethod.GET)
	public String editNewsItem(Model model, @PathVariable int newsId) {
		
		NewsItemModel newsItemModel = new NewsItemModel();
		
		if (newsId > 0) { 
		News newsItem = newsService.findById(newsId);
		newsItemModel.setId(newsItem.getId());
		newsItemModel.setTitle(newsItem.getTitle());
		newsItemModel.setBrief(newsItem.getBrief());
		newsItemModel.setNewsDate(newsItem.getNewsDate());
		newsItemModel.setContent(newsItem.getContent());
		} else {
			newsItemModel.setNewsDate(new Date());
		}
		
		model.addAttribute("newsItemModel", newsItemModel);
		
		return "news.add";
	}
}
