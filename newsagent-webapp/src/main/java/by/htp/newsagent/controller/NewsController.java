package by.htp.newsagent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import by.htp.newsagent.domain.news.NewsStatus;
import by.htp.newsagent.service.news.NewsService;

@Controller
@RequestMapping(value="/news")
public class NewsController {
	
	@Autowired
	private NewsService newsService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getNewsList(Model model) {
		
		model.addAttribute("newsList", newsService.findByStatus(NewsStatus.ACTUAL));
		
		return "news.list";
	}
}
