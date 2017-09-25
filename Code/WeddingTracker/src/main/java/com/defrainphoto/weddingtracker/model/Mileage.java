package com.defrainphoto.weddingtracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MILEAGE")
public class Mileage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="EVENTID")
	private String eventId;
	
	@Column(name="YEAR")
	private Integer year;
	
	@Column(name="TOTALMILES")
	private Integer totalMiles;

	public Mileage() {}
	
	public Mileage(String eventId, Integer year, Integer totalMiles) {
		this.eventId = eventId;
		this.year = year;
		this.totalMiles = totalMiles;
	}
	
	
	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getTotalMiles() {
		return totalMiles;
	}

	public void setTotalMiles(Integer totalMiles) {
		this.totalMiles = totalMiles;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
		Mileage other = (Mileage) obj;
		if (eventId == null) {
			if (other.eventId != null)
				return false;
		} else if (!eventId.equals(other.eventId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "" + eventId + ", " + year + ", " + totalMiles;
	}
}
