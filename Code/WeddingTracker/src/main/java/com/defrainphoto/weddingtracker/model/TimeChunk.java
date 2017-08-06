package main.java.com.defrainphoto.weddingtracker.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Set;

public class TimeChunk implements Serializable {
	private int chunkId;
	private Timeline timeline;
	private int position;
	private Time startTime;
//	private int locationId;
	private Location location;
	private Time duration;
	private String description;
	private Client client;
	private Set<Photographer> photographers;
	
	public TimeChunk() {}
		
	public TimeChunk(int chunkId, Timeline timeline, int position, Time startTime, Location location, Time duration,
			String description, Client client, Set<Photographer> photographers) {
		this.chunkId = chunkId;
		this.timeline = timeline;
		this.position = position;
		this.startTime = startTime;
		this.location = location;
		this.duration = duration;
		this.description = description;
		this.client = client;
		this.photographers = photographers;
	}

	public int getChunkId() {
		return chunkId;
	}
	
	public void setChunkId(int chunkId) {
		this.chunkId = chunkId;
	}
	
	public Timeline getTimeline() {
		return timeline;
	}
	
	public void setTimeline(Timeline timeline) {
		this.timeline = timeline;
	}
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Time getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	
//	public int getLocationId() {
//		return locationId;
//	}
//
//	public void setLocationId(int locationId) {
//		this.locationId = locationId;
//	}

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
	
	public Set<Photographer> getPhotographers() {
		return photographers;
	}
	
	public void setPhotographers(Set<Photographer> photographers) {
		this.photographers = photographers;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + chunkId;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((timeline == null) ? 0 : timeline.hashCode());
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
		if (chunkId != other.chunkId)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (timeline == null) {
			if (other.timeline != null)
				return false;
		} else if (!timeline.equals(other.timeline))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "TimeChunk [chunkId=" + chunkId + ", timeline=" + timeline + ", position=" + position + ", startTime="
				+ startTime + ", location=" + location + ", duration=" + duration + ", description=" + description
				+ ", client=" + client + ", photographers=" + photographers + "]";
	}

	
}
