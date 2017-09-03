package com.defrainphoto.weddingtracker.model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name="EVENTPHOTOGRAPHERS")
@Immutable
public class EventPhotographer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EVENTID", updatable = false, nullable = false)
	private String eventId;
	
	@Column(name = "EVENTNAME")
	private String eventName;
	
	@Column(name = "EVENTDATE")
	private Date eventDate;
	
	@Column(name = "STARTTIME")
	private Time startTime;
	
	@Column(name = "PHOTOGRAPHER")
	private String photographer;

	public String geteventId() {
		return eventId;
	}

	public void seteventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
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

	public String getPhotographer() {
		return photographer;
	}

	public void setPhotographer(String photographer) {
		this.photographer = photographer;
	}

	@Override
	public String toString() {
		return "EventPhotographers [eventId=" + eventId + ", eventName=" + eventName + ", eventDate=" + eventDate + ", startTime="
				+ startTime + ", photographer=" + photographer + "]";
	}
	
	
}
