package com.defrainphoto.weddingtracker.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

//import javax.validation.constraints.NotNull;

public class Event implements Serializable{
	private String eventId;
	@NotEmpty(message = "Event must have a name")
	private String eventName;
	private EventType eventType;
	@NotNull(message = "Please pick a Date")
	private Date eventDate;
	private Time startTime;
	private Time duration;
	private Timeline timeline;
	private String extraCost;
	private String notes;
	
//	private Set<Photographer> photographers;	
//	private Set<Client> clients;

	public Event() {}
	
	public Event(String eventId, String eventName, EventType eventType, Date eventDate, Time startTime,
			Time duration, Timeline timeline, String extraCost, String notes,
			Set<Photographer> photographers, Set<Client> clients) {
		super();
		this.eventId = eventId;
		this.eventName = eventName;
		this.eventType = eventType;
		this.eventDate = eventDate;
		this.startTime = startTime;
		this.duration = duration;
		this.timeline = timeline;
		this.extraCost = extraCost;
		this.notes = notes;
//		this.photographers = photographers;
//		this.clients = clients;
	}
	

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	
	public Time getDuration() {
		return duration;
	}

	public void setDuration(Time duration) {
		this.duration = duration;
		if (duration == null) {
			duration = new Time(0, 0, 0);
		}
	}

	public Timeline getTimeline() {
		return timeline;
	}

	public void setTimeline(Timeline timeline) {
		this.timeline = timeline;
	}

	public String getExtraCost() {
		return extraCost;
	}

	public void setExtraCost(String extraCost) {
		this.extraCost = extraCost;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

//	public Set<Photographer> getPhotographers() {
//		return photographers;
//	}

//	@NotNull
//	public void setPhotographers(Set<Photographer> photographers) {
//		if (photographers == null) {
//			return;
////			photographers = new HashSet<Photographer>();
//		}
//		this.photographers.addAll(photographers);
//	}
	
	// internal method, not hibernate for setting a photographer
//	public void setPhotographers(Photographer newPhotographer) {
//		if (photographers == null) {
//			photographers = new HashSet<Photographer>();
//		}
//		this.photographers.add(newPhotographer);
//	}

//	public Set<Client> getClients() {
//		return clients;
//	}
//
////	@NotNull
//	public void setClients(Set<Client> clients) {
//		if (this.clients == null) {
//			clients = new HashSet<Client>();
//		}
//		this.clients.addAll(clients);
//	}
//	
//	// internal method to set clients, not hibernate
//	public void setClients(Client client) {
//		if (this.clients == null) {
//			clients = new HashSet<Client>();
//		}
//		this.clients.add(client);
//	}

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
		Event other = (Event) obj;
		if (eventId == null) {
			if (other.eventId != null)
				return false;
		} else if (!eventId.equals(other.eventId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", eventName=" + eventName + ", eventType=" + eventType + ", eventDate="
				+ eventDate + ", startTime=" + startTime + ", duration=" + duration + ", timeline=" + timeline
				+ ", extraCost=" + extraCost + ", notes=" + notes + "]";
	}
}
