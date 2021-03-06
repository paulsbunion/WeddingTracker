package com.defrainphoto.weddingtracker.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;

public class Photographer implements Serializable{
	private String staffId;
	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;
	private Set<Event> events;	
	
	public Photographer() {}
	
	public Photographer(String staffId, String firstName, String lastName) {
		this.staffId = staffId;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Set<Event> getEvents() {
		return events;
	}
	public void setEvents(Set<Event> events) {
		if (this.events == null) {
			this.events = new HashSet<Event>();
		}
		if (events != null) {
			try {
				this.events.addAll((Set<Event>) events);
			}
			catch (Exception e) {
				System.out.println(e.getStackTrace());
			}
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((staffId == null) ? 0 : staffId.hashCode());
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
		Photographer other = (Photographer) obj;
		if (staffId == null) {
			if (other.staffId != null)
				return false;
		} else if (!staffId.equals(other.staffId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "" + firstName + " " + lastName;
	}
}
