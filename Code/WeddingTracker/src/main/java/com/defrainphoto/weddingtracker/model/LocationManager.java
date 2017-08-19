package com.defrainphoto.weddingtracker.model;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

public class LocationManager {
	
	Session session;
	
	public Location addLocation(Location newLocation) {
		boolean found = false;
		Location temp = findLocation(newLocation, false, true);
		
		// if already in DB, throw exception
		if (temp != null) {
			throw new EntityExistsException("Entity already Exists:  " + newLocation.toString());
		}
		
		// else, open session, save, and commit
		else {
			openSession();
			
			session.beginTransaction();
			session.save(newLocation);
			session.getTransaction().commit();
			
			temp = findLocation(newLocation, false, true);
			newLocation.setLocationId(temp.getLocationId()); 
			Hibernate.initialize(newLocation);
			closeSession();
			found = true;
		}
		
		return found ? newLocation : null;
	}
	
	public Location getLocationById(Location location) {
		Location result;
		
		result = findLocation(location, true, false);
		return result; 
	}
	
	public Location getLocationByAddress(Location location) {
		Location result;
		
		result = findLocation(location, false, true);
		return result; 
	}
	
	public Location setLocationCity(Location location, String newCity) {
		return setLocationHelper(location, newCity, null, null, null, null,
				true, false, false, false, false);
	}
	
	public Location setLocationState(Location location, String newState) {
		return setLocationHelper(location, null, newState, null, null, null,
				false, true, false, false, false);
	}

	public Location setLocationZip(Location location, String newZip) {
		return setLocationHelper(location, null, null, newZip, null, null,
				false, false, true, false, false);
	}
	
	public Location setLocationStreet(Location location, String newStreet) {
		return setLocationHelper(location, null, null, null, newStreet, null,
				false, false, false, true, false);
	}
	
	public Location setLocationDescription(Location location, String newDescription) {
		return setLocationHelper(location, null, null, null, null, newDescription,
				false, false, false, false, true);
	}
	
	public Location deleteLocationById(Location location) {
		boolean deleted = false;
		
		Location foundLocation = null;
		// check if in database
		foundLocation = findLocation(location, true, false);
		
		if (foundLocation != null) {
			deleted = deleteLocation(foundLocation);
		}

		return deleted? foundLocation : null;
	}
	
	public Location deleteLocationByAddress(Location location) {
		boolean deleted = false;
		
		Location foundLocation = null;
		// check if in database
		foundLocation = findLocation(location, false, true);
		
		if (foundLocation != null) {
			deleted = deleteLocation(foundLocation);
			if (deleted) {
			}
		}

		return deleted? foundLocation : null;
	}
	
	// helper method to delete from database. returns success / failure status
		private boolean deleteLocation(Location locationToDelete) {
			boolean success = false;
			try {
				openSession();
				
				session.beginTransaction();
				session.delete(locationToDelete);
				session.getTransaction().commit();
				closeSession();
				success = true;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			return success;
		}
	
	private Location findLocation(Location location, boolean byID, boolean byAddress) {
		boolean found = false;
		openSession();
		
		session.beginTransaction();
		
		StringBuilder queryString = new StringBuilder("from Location where ");
		buildQuery(queryString, byID, byAddress);
		
		Query query = session.createQuery(queryString.toString());
		setQueryVariables(location, query, byID, byAddress);
		
		List list = query.list();
		
		session.getTransaction().commit();
		
		if (list != null && !list.isEmpty()) {
			Location temp = (Location) list.get(0);
			location.setLocationId(temp.getLocationId());
			found = true;
		}
		
		else {
			found = false;
		}
		Hibernate.initialize(location);
		closeSession();
		
		return found ? location : null;
	}
	
	private void buildQuery(StringBuilder queryString, boolean byID, boolean byAddress) {
		
		if (byID) {
			queryString.append("locationId = :locationId ");
		}
		if (byAddress) {
			queryString.append("city = :city");
			queryString.append(" AND ");
			queryString.append("state = :state");
			queryString.append(" AND ");
			queryString.append("zip = :zip");
			queryString.append(" AND ");
			queryString.append("street = :street");
		}
	}

	private void setQueryVariables(Location location, Query query, boolean byID, boolean byAddress) {
		if (byID) {
			query.setParameter("locationId", location.getLocationId());
		}
		if (byAddress) {
			query.setParameter("city" , location.getCity());
			query.setParameter("state" , location.getState());
			query.setParameter("zip" , location.getZip());
			query.setParameter("street" , location.getStreet());
		}
	}

	private Location setLocationHelper(Location location, String newCity, String newState, String newZip, String newStreet,
			String newdescription, boolean updateCity, boolean updateState, boolean updateZip, 
			boolean updateStreet, boolean updateDescription) {
		
		// if any new value to update is null, throw exception
		if (updateCity && newCity == null) {
			 throw new IllegalArgumentException("cannot update field to Null: City");
		}
		if (updateState && newState == null) {
			throw new IllegalArgumentException("cannot update field to Null: State");
		}
		if (updateZip && newZip == null) {
			throw new IllegalArgumentException("cannot update field to Null: Zip");
		}
		if (updateStreet && newStreet == null) {
			throw new IllegalArgumentException("cannot update field to Null: Street");
		}
		
		// store update status
		boolean valid = false;
		// Strings to store old data
		String oldCity = location.getCity();
		String oldState = location.getState();
		String oldZip = location.getZip();
		String oldStreet = location.getStreet();
		String oldDescription = location.getDescription();
		
		// set new parameters
		if (!updateCity) {
			newCity = oldCity;
		}
		if (!updateState) {
			newState = oldState;
		}
		if (!updateZip) {
			newZip = oldZip;
		}
		if (!updateStreet) {
			newStreet = oldStreet;
		}
		if (!updateDescription) {
			newdescription = oldDescription;
		}

		// first try to find the Location and optional new named Location
		Location foundLocation = findLocation(location, false, true);
		Location foundLocationNewAddress = null;
		
		// if updating the Address street, check if the new Address street already exists
		if (updateStreet) {
			foundLocationNewAddress = new Location(null, newCity, newState, newZip, newStreet, newdescription);
			
			foundLocationNewAddress = findLocation(foundLocationNewAddress, false, true);
			
			if (foundLocationNewAddress != null) {
				throw new EntityExistsException("new entity already exists: " + foundLocationNewAddress.toString());
			}
		}
		
		// If the Client exists, Update the client and set old parameters to new parameters
		try {
			if (foundLocation != null && foundLocationNewAddress == null) {
				foundLocation.setCity(newCity);
				foundLocation.setState(newState);
				foundLocation.setZip(newZip);
				foundLocation.setStreet(newStreet);
				foundLocation.setDescription(newdescription);
				
				// update the client id and new last name
				location.setLocationId(foundLocation.getLocationId());
				location.setCity(newCity);
				location.setState(newState);
				location.setZip(newZip);
				location.setStreet(newStreet);
				location.setDescription(newdescription);
				
				// open new session and commit
				openSession();
				
				session.beginTransaction();
				session.saveOrUpdate(foundLocation);
				session.getTransaction().commit();
				
				Hibernate.initialize(foundLocation);
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
				location.setCity(oldCity);
				location.setState(oldState);
				location.setZip(oldZip);
				location.setStreet(oldStreet);
				location.setDescription(oldDescription);
			}
		}
		return valid ? location : null;
	}
	
	private void openSession() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	private void closeSession() {
		session.close();
	}
}
