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
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((autoRemind == null) ? 0 : autoRemind.hashCode());
		result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
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
		Client other = (Client) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (autoRemind == null) {
			if (other.autoRemind != null)
				return false;
		} else if (!autoRemind.equals(other.autoRemind))
			return false;
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Client [clientId=" + clientId + ", firstName=" + firstName + ", lastName=" + lastName + ", address="
				+ address + ", phoneNumber=" + phoneNumber + ", email=" + email + ", autoRemind=" + autoRemind + "]";
	}
	
	
}
