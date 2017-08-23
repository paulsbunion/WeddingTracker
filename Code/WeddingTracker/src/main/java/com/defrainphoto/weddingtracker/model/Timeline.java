package com.defrainphoto.weddingtracker.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Set;

public class Timeline implements Serializable{

	private String eventId;
	private Event event;
	private Set<TimeChunk> timeChunks;
	private Time startTime;
	private Time totalTime;
	
	public Timeline() {}
	
	public Timeline(String eventId, Event event, Set<TimeChunk> timeChunks, Time startTime, Time totalTime) {
		super();
		this.eventId = eventId;
		this.event = event;
		this.timeChunks = timeChunks;
		this.startTime = startTime;
		this.totalTime = totalTime;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Set<TimeChunk> getTimeChunks() {
		return timeChunks;
	}

	public void setTimeChunks(Set<TimeChunk> timeChunks) {
		this.timeChunks = timeChunks;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Time totalTime) {
		this.totalTime = totalTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
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
		Timeline other = (Timeline) obj;
		if (eventId == null) {
			if (other.eventId != null)
				return false;
		} else if (!eventId.equals(other.eventId))
			return false;
		return true;
	}

	@Override
	public String toString() {
//		if (timeChunks == null) {
			return "" + eventId + " " + startTime + " " + totalTime;
//		}
//		else {
//			return "Timeline [eventId=" + eventId + ", timeChunks=" + timeChunks + ", startTime=" + startTime
//					+ ", totalTime=" + totalTime + "]";
//		}
	}
	
	
}
