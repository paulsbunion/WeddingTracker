package com.defrainphoto.weddingtracker.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Set;

public class TimeChunk implements Serializable {
	private String chunkId;
	private Timeline timeline;
	private int position;
	private Time startTime;
//	private int locationId;
	private Location location;
//	private Time duration;
	private String durationHr;
	private String durationMin;
	private String description;
	private Client client;
	private Set<Photographer> photographers;
	private String notes;
	private String trackMileage;
	
	private String eventId;
	
	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public TimeChunk() {}
		
	public TimeChunk(String chunkId, Timeline timeline, int position, Time startTime, Location location, 
			String durationHr, String durationMin,
			String description, Client client, Set<Photographer> photographers) {
		this.chunkId = chunkId;
		this.timeline = timeline;
		this.position = position;
		this.startTime = startTime;
		this.location = location;
//		this.duration = duration;
		this.durationHr = durationHr;
		this.durationMin = durationMin;
		this.description = description;
		this.client = client;
		this.photographers = photographers;
	}

	public String getChunkId() {
		return chunkId;
	}
	
	public void setChunkId(String chunkId) {
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
	
	public String getDurationHr() {
		return durationHr;
	}

	public void setDurationHr(String durationHr) {
		this.durationHr = durationHr;
	}

	public String getDurationMin() {
		return durationMin;
	}

	public void setDurationMin(String durationMin) {
		this.durationMin = durationMin;
	}

//	public Time getDuration() {
//		return duration;
//	}
//	
//	public void setDuration(Time duration) {
//		this.duration = duration;
//	}
	
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
	
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getTrackMileage() {
		return trackMileage;
	}

	public void setTrackMileage(String trackMileage) {
		this.trackMileage = trackMileage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chunkId == null) ? 0 : chunkId.hashCode());
		result = prime * result + ((timeline.getEventId() == null) ? 0 : timeline.getEventId().hashCode());
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
		if (chunkId == null) {
			if (other.chunkId != null)
				return false;
		} else if (!chunkId.equals(other.chunkId))
			return false;
		if (timeline == null) {
			if (other.timeline != null)
				return false;
		} else if (!timeline.getEventId().equals(other.timeline.getEventId()))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "TimeChunk [chunkId=" + chunkId + ", position=" + position + ", startTime="
				+ startTime + ", location=" + location + ", duration=" + durationHr + ":" + durationMin
				+ ", description=" + description
				+ ", client=" + client + ", photographers=" + photographers + ", eventId=" + eventId + "]";
	}

	
}
