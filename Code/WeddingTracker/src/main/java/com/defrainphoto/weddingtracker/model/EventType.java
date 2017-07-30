package main.java.com.defrainphoto.weddingtracker.model;

import java.io.Serializable;

public class EventType implements Serializable{
	private int eventTypeId;
	private String eventType;
	private String baseCost;
	
	public EventType() {}
	
	public EventType(int eventTypeId, String eventType, String baseCost) {
		this.eventTypeId = eventTypeId;
		this.eventType = eventType;
		this.baseCost = baseCost;
	}

	public int getEventTypeId() {
		return eventTypeId;
	}
	
	public void setEventTypeId(int eventTypeId) {
		this.eventTypeId = eventTypeId;
	}
	
	public String getEventType() {
		return eventType;
	}
	
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
	public String getBaseCost() {
		return baseCost;
	}
	
	public void setBaseCost(String baseCost) {
		this.baseCost = baseCost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseCost == null) ? 0 : baseCost.hashCode());
		result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
		result = prime * result + eventTypeId;
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
		EventType other = (EventType) obj;
		if (baseCost == null) {
			if (other.baseCost != null)
				return false;
		} else if (!baseCost.equals(other.baseCost))
			return false;
		if (eventType == null) {
			if (other.eventType != null)
				return false;
		} else if (!eventType.equals(other.eventType))
			return false;
		if (eventTypeId != other.eventTypeId)
			return false;
		return true;
	}
	
}
