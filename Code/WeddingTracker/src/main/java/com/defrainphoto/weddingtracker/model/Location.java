package main.java.com.defrainphoto.weddingtracker.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class Location implements Serializable{
	private String locationId;
	private String city;
	private String state;
	private String zip;
	private String street;
	private String description;
	
	public Location() {};
	public Location(String locationId, String city, String state, String zip, String street, String description) {
		this.locationId = locationId;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.street = street;
		this.description = description;
	};
	
	public String getLocationId() {
		return locationId;
	}
	
	@NotNull
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	
	public String getCity() {
		return city;
	}
	
	@NotNull
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}
	
	@NotNull
	public void setState(String state) {
		this.state = state;
	}
	
	public String getZip() {
		return zip;
	}
	
	@NotNull
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public String getStreet() {
		return street;
	}
	
	@NotNull
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((locationId == null) ? 0 : locationId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (locationId == null) {
			if (other.locationId != null)
				return false;
		} else if (!locationId.equals(other.locationId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Location [locationId=" + locationId + ", city=" + city + ", state=" + state + ", zip=" + zip
				+ ", street=" + street + ", description=" + description + "]";
	}
	

}
