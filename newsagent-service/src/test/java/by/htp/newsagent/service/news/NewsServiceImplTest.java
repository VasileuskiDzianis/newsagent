package by.htp.newsagent.service.news;

import org.junit.Test;

public class NewsServiceImplTest extends NewsServiceImpl {
	NewsService newsService = new NewsServiceImpl();

	@Test(expected = IllegalArgumentException.class)
	public void testFindByStatus() {
		newsService.findByStatus(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testArchiveOnePieceOfNews() {
		newsService.archiveOnePieceOfNews(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testArchiveSeveralNews() {
		newsService.archiveSeveralNews(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveNews() {
		newsService.saveNews(null);
	}
}
