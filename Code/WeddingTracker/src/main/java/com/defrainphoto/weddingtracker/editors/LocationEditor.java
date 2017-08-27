package com.defrainphoto.weddingtracker.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

import com.defrainphoto.weddingtracker.model.Location;
import com.defrainphoto.weddingtracker.model.LocationManager;

public class LocationEditor  extends PropertyEditorSupport{
	
	LocationManager locationManager = new LocationManager();
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasText(text)) {
			String[] data = text.split(", ");
			String description = "";
			if (data.length > 4) {
				description = data[4];
			}
			Location location = new Location("", data[0], data[1], data[2], data[3], description);
			location = locationManager.getLocationByAddress(location);
			setValue(location);
		}
	}
	
	@Override
    public String getAsText() {
		Location location = (Location) getValue();
        if (location != null) {
            return location.toString();
        } else {
            return "";
        }
    }
}
