package main.java.com.defrainphoto.weddingtracker.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class Location implements Serializable{
	private int locationId;
	private String city;
	private String state;
	private String zip;
	private String address;
	private String description;
	
	public Location() {};
	public Location(int locationId, String city, String state, String zip, String address, String description) {
		this.locationId = locationId;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.address = address;
		this.description = description;
	};
	
	public int getLocationId() {
		return locationId;
	}
	
	@NotNull
	public void setLocationId(int locationId) {
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
	
	public String getAddress() {
		return address;
	}
	
	@NotNull
	public void setAddress(String address) {
		this.address = address;
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
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + locationId;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
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
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (locationId != other.locationId)
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (zip == null) {
			if (other.zip != null)
				return false;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Location [locationId=" + locationId + ", city=" + city + ", state=" + state + ", zip=" + zip
				+ ", address=" + address + ", description=" + description + "]";
	}
	

}
