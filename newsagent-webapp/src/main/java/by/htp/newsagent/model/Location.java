package by.htp.newsagent.model;

public enum Location {

	ABOUT("label.locationAbout"), NEWS("label.locationNews"), NEWS_LIST("label.locationNewsList"), NEWS_VIEW(
			"label.locationNewsView"), NEWS_ADD("label.locationNewsAdd"), NEWS_EDIT("label.locationNewsEdit"), ERROR("label.error");

	private String label;

	Location(String label) {
		this.label = label;
	}

	public String getLabel() {

		return this.label;
	}
}
