package main.java.com.defrainphoto.weddingtracker.model;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

public class Client implements Serializable{
	private String clientId;
	private String firstName;
	private String lastName;
	private String address;
	private String phoneNumber;
	private String email;
	private String autoRemind;
	private Set<Event> events;
	
	public Client() {}
	
	public Client(String clientId, String firstName, String lastName, String address, String phoneNumber, String email,
			String autoRemind) {
		this.clientId = clientId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.autoRemind = autoRemind;
	}

	public Client(Client client) {
		this.clientId = client.getClientId();
		this.firstName = client.getFirstName();
		this.lastName = client.getLastName();
		this.address = client.getAddress();
		this.phoneNumber = client.getPhoneNumber();
		this.email = client.getEmail();
		this.autoRemind = client.getAutoRemind();
	}

	public String getClientId() {
		return clientId;
	}
	
	@NotNull
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	@NotNull
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	@NotNull
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getAddress() {
		return address;
	}
	
	@NotNull
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	@NotNull
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getEmail() {
		return email;
	}
	
	@NotNull
	@Email
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getAutoRemind() {
		return autoRemind;
	}
	
	@NotNull
	public void setAutoRemind(String autoRemind) {
		this.autoRemind = autoRemind;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
//		if (getClass() != obj.getClass())
//			return false;
		if (!(obj instanceof Client))
			return false;
		Client other = (Client) obj;
		if (clientId == null) {
			if (other.clientId != null) 
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Client [clientId=" + clientId + ", firstName=" + firstName + ", lastName=" + lastName + ", address="
				+ address + ", phoneNumber=" + phoneNumber + ", email=" + email + ", autoRemind=" + autoRemind + "]";
	}
	
	
}
