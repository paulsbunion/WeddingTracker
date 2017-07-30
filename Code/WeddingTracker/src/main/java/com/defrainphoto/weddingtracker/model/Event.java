package main.java.com.defrainphoto.weddingtracker.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class Event implements Serializable{
	private int eventId;
	//private Set<Location> locations;
	//private Photographer primaryPhotographer;
	private String multiplePhotographers;
	//private Set<Photographer> additionalPhotographers;
	private Set<Photographer> photographers;	
	private EventType eventType;
	private String multiClient;
	private Set<Client> clients;
	private Date eventDate;
	//private Timeline eventTimeline;
	private String additionalCost;
	private String description;
	private Time startTime;
	
	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

//	public Set<Location> getLocations() {
//		return locations;
//	}
//	
//	@NotNull
//	public void setLocations(Location location) {
//		if (this.locations == null) {
//			locations = new HashSet<Location>();
//		}
//		this.locations.add(location);
//	}

//	public Photographer getPrimaryPhotographer() {
//		return primaryPhotographer;
//	}

//	@NotNull
//	public void setPrimaryPhotographer(Photographer primaryPhotographer) {
//		this.primaryPhotographer = primaryPhotographer;
//	}

	public Set<Photographer> getPhotographers() {
		return photographers;
	}

//	@NotNull
//	public void setAdditionalPhotographers(Photographer newPhotographer) {
//		this.photographers.add(newPhotographer);
//	}
	
	@NotNull
	public void setPhotographers(Set<Photographer> photographers) {
		if (photographers == null) {
			photographers = new HashSet<Photographer>();
		}
		this.photographers.addAll(photographers);
	}
	
	// internal method, not hibernate for setting a photographer
	public void setPhotographers(Photographer newPhotographer) {
		if (photographers == null) {
			photographers = new HashSet<Photographer>();
		}
		this.photographers.add(newPhotographer);
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public String getMultiClient() {
		return multiClient;
	}

	@NotNull
	public void setMultiClient(String multiClient) {
		this.multiClient = multiClient;
	}

	public Set<Client> getClients() {
		return clients;
	}

	@NotNull
	public void setClients(Set<Client> clients) {
		if (this.clients == null) {
			clients = new HashSet<Client>();
		}
		this.clients.addAll(clients);
	}
	
	// internal method to set clients, not hibernate
	public void setClients(Client client) {
		if (this.clients == null) {
			clients = new HashSet<Client>();
		}
		this.clients.add(client);
	}

	public Date getEventDate() {
		return eventDate;
	}

	@NotNull
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

//	public Timeline getEventTimeline() {
//		return eventTimeline;
//	}
//
//	public void setEventTimeline(Timeline eventTimeline) {
//		this.eventTimeline = eventTimeline;
//	}

	public String getAdditionalCost() {
		return additionalCost;
	}

	@NotNull
	public void setAdditionalCost(String additionalCost) {
		this.additionalCost = additionalCost;
	}

	public String getDescription() {
		return description;
	}

	@NotNull
	@NotBlank
	public void setDescription(String description) {
		this.description = description;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public String isMultiplePhotographers() {
		return multiplePhotographers;
	}

	@NotNull
	public void setMultiplePhotographers(String hasMultiple) {
		this.multiplePhotographers = hasMultiple;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((additionalCost == null) ? 0 : additionalCost.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((eventDate == null) ? 0 : eventDate.hashCode());
		result = prime * result + eventId;
		result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
		result = prime * result + ((multiClient == null) ? 0 : multiClient.hashCode());
		result = prime * result + ((multiplePhotographers == null) ? 0 : multiplePhotographers.hashCode());
		result = prime * result + ((photographers == null) ? 0 : photographers.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
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
		if (additionalCost == null) {
			if (other.additionalCost != null)
				return false;
		} else if (!additionalCost.equals(other.additionalCost))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (eventDate == null) {
			if (other.eventDate != null)
				return false;
		} else if (!eventDate.equals(other.eventDate))
			return false;
		if (eventId != other.eventId)
			return false;
		if (eventType == null) {
			if (other.eventType != null)
				return false;
		} else if (!eventType.equals(other.eventType))
			return false;
		if (multiClient == null) {
			if (other.multiClient != null)
				return false;
		} else if (!multiClient.equals(other.multiClient))
			return false;
		if (multiplePhotographers == null) {
			if (other.multiplePhotographers != null)
				return false;
		} else if (!multiplePhotographers.equals(other.multiplePhotographers))
			return false;
		if (photographers == null) {
			if (other.photographers != null)
				return false;
		} else if (!photographers.equals(other.photographers))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}
}
