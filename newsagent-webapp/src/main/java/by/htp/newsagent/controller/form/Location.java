package by.htp.newsagent.controller.form;

public enum Location {

	ABOUT("messages.label.locationAbout"), 
	NEWS("messages.label.locationNews"), 
	NEWS_LIST("messages.label.locationNewsList"), 
	NEWS_VIEW("messages.label.locationNewsView"), 
	NEWS_ADD("messages.label.locationNewsAdd"), 
	NEWS_EDIT("messages.label.locationNewsEdit"), 
	ERROR("messages.label.error");

	private String label;

	Location(String label) {
		this.label = label;
	}

	public String getLabel() {

		return this.label;
	}
}
