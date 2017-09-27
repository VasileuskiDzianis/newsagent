package by.htp.newsagent.controller.form;

public class LocationWebModel {
	public static final String ALIAS = "locationModel";
	
	private Location currentLocation;
	private Location previousLocation;

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}

	public Location getPreviousLocation() {
		return previousLocation;
	}

	public void setPreviousLocation(Location previousLocation) {
		this.previousLocation = previousLocation;
	}
}
