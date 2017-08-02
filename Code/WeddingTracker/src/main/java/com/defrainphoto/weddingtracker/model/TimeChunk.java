package main.java.com.defrainphoto.weddingtracker.model;

import java.sql.Time;

public class TimeChunk {
	String timelineId;
	Time startTime;
	Location location;
	Time duration;
	String description;
	Client client;
	Photographer photographer;
	
	public TimeChunk() {}
	
	public TimeChunk(String timelineId, Time startTime, Location location, Time duration, String description,
			Client client, Photographer photographer) {
		this.timelineId = timelineId;
		this.startTime = startTime;
		this.location = location;
		this.duration = duration;
		this.description = description;
		this.client = client;
		this.photographer = photographer;
	}

	public String getTimelineId() {
		return timelineId;
	}
	
	public void setTimelineId(String timelineId) {
		this.timelineId = timelineId;
	}
	
	public Time getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Time getDuration() {
		return duration;
	}
	
	public void setDuration(Time duration) {
		this.duration = duration;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Client getClient() {
		return client;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public Photographer getPhotographer() {
		return photographer;
	}
	
	public void setPhotographer(Photographer photographer) {
		this.photographer = photographer;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((photographer == null) ? 0 : photographer.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((timelineId == null) ? 0 : timelineId.hashCode());
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
		TimeChunk other = (TimeChunk) obj;
		if (photographer == null) {
			if (other.photographer != null)
				return false;
		} else if (!photographer.equals(other.photographer))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (timelineId == null) {
			if (other.timelineId != null)
				return false;
		} else if (!timelineId.equals(other.timelineId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "TimeChunk [timelineId=" + timelineId + ", startTime=" + startTime + ", location=" + location
				+ ", duration=" + duration + ", description=" + description + ", client=" + client + ", photographer="
				+ photographer + "]";
	}

	
}
