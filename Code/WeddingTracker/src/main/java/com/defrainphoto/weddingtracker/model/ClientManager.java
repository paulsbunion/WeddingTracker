package main.java.com.defrainphoto.weddingtracker.model;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

public class ClientManager {

	Session session;
	
	public Client addClient(Client newClient) {

		boolean found = false;
		Client temp = findClient(newClient, false, true, true, false, false);
		
		// if already in DB, throw exception
		if (temp != null) {
			throw new EntityExistsException("Entity already Exists:  " + newClient .toString());
		}
		
		// else, open session, save, and commit
		else {
			openSession();
			
			session.beginTransaction();
			session.save(newClient);
			session.getTransaction().commit();
			
			temp = findClient(newClient, false, true, true, false, false);
			newClient.setClientId(temp.getClientId()); 
			Hibernate.initialize(newClient);
			closeSession();
			found = true;
		}
		
		return found ? newClient : null;
	}

	public Client getClientByName(Client client) {
		Client result;
		
		result = findClient(client, false, true, true, false, false);
		return result; 
	}
	
	public Client getClientById(Client client) {
		Client result;
		
		result = findClient(client, true, false, false, false, false);
		return result; 
	}
	
	public Client getClientByPhoneNumber(Client client) {
		Client result;
		
		result = findClient(client, false, false, false, true, false);
		return result; 
	}
	
	public Client getClientByEmail(Client client) {
		Client result;
		
		result = findClient(client, false, false, false, false, true);
		return result; 
	}
	
	private Client findClient(Client client, boolean byID, boolean byFName, boolean byLname, boolean byPhoneNumber, boolean byEmail) {
		boolean found = false;
		openSession();
		
		session.beginTransaction();
		
		StringBuilder queryString = new StringBuilder("from Client where ");
		buildQuery(queryString, byID, byFName, byLname, byPhoneNumber, byEmail);
		
		Query query = session.createQuery(queryString.toString());
		setQueryVariables(client, query, byID, byFName, byLname, byPhoneNumber, byEmail);
		
		List list = query.list();
		
		session.getTransaction().commit();
		
		if (list != null && !list.isEmpty()) {
			Client temp = (Client) list.get(0);
			client.setClientId(temp.getClientId());
			found = true;
		}
		
		else {
			found = false;
		}
		Hibernate.initialize(client);
		closeSession();
		
		return found ? client : null;
	}
	
	private void setQueryVariables(Client client, Query query, boolean byID, boolean byFName, boolean byLname, 
			boolean byPhoneNumber, boolean byEmail) {
		if (byID) {
			query.setParameter("clientId", client.getClientId());
		}
		if (byFName) {
			query.setParameter("fname", client.getFirstName());
		}
		if (byLname) {
			query.setParameter("lname", client.getLastName());
		}
		if (byPhoneNumber) {
			query.setParameter("pnumber", client.getPhoneNumber());
		}
		if (byEmail) {
			query.setParameter("email", client.getEmail());
		}
		
	}

	private void buildQuery(StringBuilder queryString, boolean byID, boolean byFName, boolean byLname,
			boolean byPhoneNumber, boolean byEmail) {
		
		boolean moreThanOne = false;
		
		if (byID) {
			queryString.append("clientId = :clientId ");
			moreThanOne = true;
		}
		if (byFName) {
			if (moreThanOne) {
				queryString.append("AND ");
			}
			queryString.append("firstName = :fname ");
			moreThanOne = true;			
		}
		
		if (byLname) {
			if (moreThanOne) {
				queryString.append("AND ");
			}
			queryString.append("lastName = :lname ");
			moreThanOne = true;
		}
		if (byPhoneNumber) {
			if (moreThanOne) {
				queryString.append("AND ");
			}
			queryString.append("phoneNumber = :pnumber ");
			moreThanOne = true;			
		}
		if (byEmail) {
			if (moreThanOne) {
				queryString.append("AND ");
			}
			queryString.append("email = :email ");
			moreThanOne = true;			
		}
	}


	public Client setClientFirstName(Client client, String newFirstName) {
		return setClientHelper(client, newFirstName, null, null, null, null, null,
				true, false, false, false, false, false);
	}
	
	public Client setClientLastName(Client client, String newLastName) {
		return setClientHelper(client, null, newLastName, null, null, null, null,
				false, true, false, false, false, false);
	}
	
	public Client setClientFullName(Client client, String newFirstName, String newLastName) {
		return setClientHelper(client, newFirstName, newLastName, null, null, null, null,
				true, true, false, false, false, false);
	}
	
	public Client setClientAddress(Client client, String newAddress) {
		return setClientHelper(client, null, null, newAddress, null, null, null,
				false, false, true, false, false, false);
	}
	
	public Client setClientEmail(Client client, String newEmail) {
		return setClientHelper(client, null, null, null, newEmail, null, null,
				false, false, false, true, false, false);
	}
	
	public Client setClientPhoneNumber(Client client, String newPhoneNumber) {
		return setClientHelper(client, null, null, null, null, newPhoneNumber, null,
				false, false, false, false, true, false);
	}
	
	public Client setClientAutoRemind(Client client, String newAutoRemind) {
		return setClientHelper(client, null, null, null, null, null, newAutoRemind,
				false, false, false, false, false, true);
	}
	
	private Client setClientHelper(Client client, String newFirstName, String newLastName, String newAddress, String newPhoneNumber,
			String newEmail, String newAutoRemind, boolean updateFirstName, boolean updateLastName, boolean updateAddress, 
			boolean updatePhoneNumber, boolean updateEmail, boolean UpdateAutoRemind) {
		
		// if any new value to update is null, throw exception
		if (updateFirstName && newFirstName == null) {
			 throw new IllegalArgumentException("cannot update field to Null: firstName");
		}
		if (updateLastName && newLastName == null) {
			throw new IllegalArgumentException("cannot update field to Null: lastName");
		}
		if (updateAddress && newAddress == null) {
			throw new IllegalArgumentException("cannot update field to Null: Address");
		}
		if (updatePhoneNumber && newPhoneNumber == null) {
			throw new IllegalArgumentException("cannot update field to Null: phoneNumber");
		}
		if (updateEmail && newEmail == null) {
			throw new IllegalArgumentException("cannot update field to Null: email");
		}
		if (UpdateAutoRemind && newAutoRemind == null) {
			throw new IllegalArgumentException("cannot update field to Null: autoRemind");
		}
		
		// do not allow update of autoremind to anything but y/n
		if (UpdateAutoRemind && !(newAutoRemind.equalsIgnoreCase("Y") || newAutoRemind.equalsIgnoreCase("N"))) {
			throw new IllegalArgumentException("field must be Y/N: autoRemind, but was: " + newAutoRemind);
		}
		
		// store update status
		boolean valid = false;
		// Strings to store old data
		String oldFirstName = client.getFirstName();
		String oldLastName = client.getLastName();
		String oldAddress = client.getAddress();
		String oldPhoneNumber = client.getPhoneNumber();
		String oldEmail = client.getEmail();
		String oldAutoRemind = client.getAutoRemind();
		
		// set new parameters
		if (!updateFirstName) {
			newFirstName = oldFirstName;
		}
		if (!updateLastName) {
			newLastName = oldLastName;
		}
		if (!updateAddress) {
			newAddress = oldAddress;
		}
		if (!updatePhoneNumber) {
			newPhoneNumber = oldPhoneNumber;
		}
		if (!updateEmail) {
			newEmail = oldEmail;
		}
		if (!UpdateAutoRemind) {
			newAutoRemind = oldAutoRemind;
		}
		
		// first try to find the Client and optional new named client
		Client foundClient = findClient(client, false, true, true, false, false);
		Client foundClientNewName = null;
		
		// if updating the name, check if the new name already exists
		if (updateFirstName || updateLastName) {
			foundClientNewName = new Client(null, newFirstName, newLastName, client.getAddress(), client.getPhoneNumber(),
					client.getEmail(), client.getAutoRemind());
			
			foundClientNewName = findClient(foundClientNewName, false, true, true, false, false);
			
			if (foundClientNewName != null) {
				throw new EntityExistsException("new entity already exists: " + foundClientNewName.toString());
			}
		}
		
		// If the Client exists, Update the client and set old parameters to new parameters
		try {
			if (foundClient != null && foundClientNewName == null) {
				foundClient.setFirstName(newFirstName);
				foundClient.setLastName(newLastName);
				foundClient.setAddress(newAddress);
				foundClient.setPhoneNumber(newPhoneNumber);
				foundClient.setEmail(newEmail);
				foundClient.setAutoRemind(newAutoRemind);
				
				// update the client id and new last name
				client.setClientId(foundClient.getClientId());
				client.setFirstName(newFirstName);
				client.setLastName(newLastName);
				client.setAddress(newAddress);
				client.setPhoneNumber(newPhoneNumber);
				client.setEmail(newEmail);
				client.setAutoRemind(newAutoRemind);
				
				// open new session and commit
				openSession();
				
				session.beginTransaction();
				session.saveOrUpdate(foundClient);
				session.getTransaction().commit();
				
				Hibernate.initialize(foundClient);
				closeSession();
				
				valid = true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		finally {
			
			// if not valid, reset to old name
			if (!valid) {
				client.setFirstName(oldFirstName);
				client.setLastName(oldLastName);
				client.setAddress(oldAddress);
				client.setPhoneNumber(oldPhoneNumber);
				client.setEmail(oldEmail);
				client.setAutoRemind(oldAutoRemind);
			}
		}
		return valid ? client : null;
	}
	
	private void openSession() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	private void closeSession() {
		session.close();
	}

	/**
	 * Attempts to delete a Client by <b>Id</b> from the database
	 * @param client the <code>Client</code> to delete
	 * @return deleted <code>Client</code> if found, <code>null</code> otherwise
	 */
	public Client DeleteClientById(Client client) {
		boolean deleted = false;
		
		Client foundClient = null;
		// check if in database
		foundClient = findClient(client, true, false, false, false, false);
		
		if (foundClient != null) {
			deleted = deleteClient(foundClient);
		}

		return deleted? foundClient : null;
	}
	
	public Client DeleteClientByName(Client client) {
		boolean deleted = false;
		
		Client foundClient = null;
		// check if in database
		foundClient = findClient(client, false, true, true, false, false);
		
		if (foundClient != null) {
			deleted = deleteClient(foundClient);
			if (deleted) {
			}
		}

		return deleted? foundClient : null;
	}

	// helper method to delete from database. returns success / failure status
	private boolean deleteClient(Client clientToDelete) {
		boolean success = false;
		try {
			openSession();
			
			session.beginTransaction();
			session.delete(clientToDelete);
			session.getTransaction().commit();
			closeSession();
			success = true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}

	

}
