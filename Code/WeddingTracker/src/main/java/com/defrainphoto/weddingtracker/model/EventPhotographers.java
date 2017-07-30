package main.java.com.defrainphoto.weddingtracker.model;

import java.io.Serializable;

public class EventPhotographers implements Serializable{
	private int eventId;
	private int staffId;
	private String isMainPhotographer;
	
	public EventPhotographers() {}
	
	public EventPhotographers(int eventId, int staffId, String isMainPhotographer) {
		this.eventId = eventId;
		this.staffId = staffId;
		this.isMainPhotographer = isMainPhotographer;
	}
	
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public int getStaffId() {
		return staffId;
	}
	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}
	public String getIsMainPhotographer() {
		return isMainPhotographer;
	}
	public void setIsMainPhotographer(String isMainPhotographer) {
		this.isMainPhotographer = isMainPhotographer;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + eventId;
		result = prime * result + ((isMainPhotographer == null) ? 0 : isMainPhotographer.hashCode());
		result = prime * result + staffId;
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
		EventPhotographers other = (EventPhotographers) obj;
		if (eventId != other.eventId)
			return false;
		if (isMainPhotographer == null) {
			if (other.isMainPhotographer != null)
				return false;
		} else if (!isMainPhotographer.equals(other.isMainPhotographer))
			return false;
		if (staffId != other.staffId)
			return false;
		return true;
	}

}
