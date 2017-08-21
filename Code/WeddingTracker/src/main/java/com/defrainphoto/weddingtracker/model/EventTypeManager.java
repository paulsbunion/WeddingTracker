package com.defrainphoto.weddingtracker.model;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

public class EventTypeManager {
Session session;
	
	public EventType addEventType(EventType newEventType) {

		boolean found = false;
		EventType temp = findEventType(newEventType, false, true);
		
		// if already in DB, throw exception
		if (temp != null) {
			throw new EntityExistsException("Entity already Exists:  " + newEventType.toString());
		}
		
		// else, open session, save, and commit
		else {
			openSession();
			
			session.beginTransaction();
			session.save(newEventType);
			session.getTransaction().commit();
			
			temp = findEventType(newEventType, false, true);
			newEventType.setEventTypeId(temp.getEventTypeId()); 
			Hibernate.initialize(newEventType);
			closeSession();
			found = true;
		}
		
		return found ? newEventType : null;
	}

	public EventType getEventTypeByName(EventType eventType) {
		EventType result;
		
		result = findEventType(eventType, false, true);
		return result; 
	}
	
	public EventType getEventTypeById(EventType eventType) {
		EventType result;
		
		result = findEventType(eventType, true, false);
		return result; 
	}
	
	
	public List<EventType> getAllEventTypes() {
		boolean found = false;
		List<EventType> result = null;
		openSession();
		
		session.beginTransaction();
		StringBuilder queryString = new StringBuilder("from EventType");
		Query query = session.createQuery(queryString.toString());
		
		List list = query.list();
		
		session.getTransaction().commit();
		
		if (list != null && !list.isEmpty()) {
			result  = (List<EventType>) list;
			found = true;
		}
		
		else {
			found = false;
		}
		Hibernate.initialize(list);
		closeSession();
		
		return found ? list : null;
	}
	
	private EventType findEventType(EventType eventType, boolean byID, boolean byName) {
		boolean found = false;
		openSession();
		
		session.beginTransaction();
		
		StringBuilder queryString = new StringBuilder("from EventType where ");
		buildQuery(queryString, byID, byName);
		
		Query query = session.createQuery(queryString.toString());
		setQueryVariables(eventType, query, byID, byName);
		
		List list = query.list();
		
		session.getTransaction().commit();
		
		if (list != null && !list.isEmpty()) {
			EventType temp = (EventType) list.get(0);
			eventType.setEventTypeId(temp.getEventTypeId());
			found = true;
		}
		
		else {
			found = false;
		}
		Hibernate.initialize(eventType);
		closeSession();
		
		return found ? eventType : null;
	}
	
	private void setQueryVariables(EventType eventType, Query query, boolean byID, boolean byName) {
		if (byID) {
			query.setParameter("eventTypeId", eventType.getEventTypeId());
		}
		if (byName) {
			query.setParameter("name", eventType.getEventType());
		}
	}

	private void buildQuery(StringBuilder queryString, boolean byID, boolean byName) {
		
		boolean moreThanOne = false;
		
		if (byID) {
			queryString.append("eventTypeId = :eventTypeId ");
			moreThanOne = true;
		}
		if (byName) {
			if (moreThanOne) {
				queryString.append("AND ");
			}
			queryString.append("eventType = :name ");
			moreThanOne = true;			
		}
	}


	public EventType setEventTypeName(EventType eventType, String newName) {
		return setEventTypeHelper(eventType, newName, null, true, false);
	}
	
	public EventType setEventTypeBaseCost(EventType eventType, String newBaseCost) {
		return setEventTypeHelper(eventType, null, newBaseCost, false, true);
	}
	
	
	private EventType setEventTypeHelper(EventType eventType, String newName, String newBaseCost,
			boolean updateName, boolean UpdateBaseCost) {
		
		// if any new value to update is null, throw exception
		if (updateName && newName == null) {
			 throw new IllegalArgumentException("cannot update field to Null: name");
		}
		if (UpdateBaseCost && newBaseCost == null) {
			throw new IllegalArgumentException("cannot update field to Null: baseCost");
		}
		
		// store update status
		boolean valid = false;
		// Strings to store old data
		String oldName = eventType.getEventType();
		String oldBaseCost = eventType.getBaseCost();
		
		// set new parameters
		if (!updateName) {
			newName = oldName;
		}
		if (!UpdateBaseCost) {
			newBaseCost = oldBaseCost;
		}
		
		// first try to find the Client and optional new named client
		EventType foundEventType = findEventType(eventType, false, true);
		EventType foundEventTypeNewName = null;
		
		// if updating the name, check if the new name already exists
		if (updateName) {
			foundEventTypeNewName = new EventType(null, newName, newBaseCost);
			
			foundEventTypeNewName = findEventType(foundEventTypeNewName, false, true);
			
			if (foundEventTypeNewName != null) {
				throw new EntityExistsException("new entity already exists: " + foundEventTypeNewName.toString());
			}
		}
		
		// If the EventType exists, Update the eventType and set old parameters to new parameters
		try {
			if (foundEventType != null && foundEventTypeNewName == null) {
				foundEventType.setEventType(newName);
				foundEventType.setBaseCost(newBaseCost);
				
				// update the eventType id and new  name
				eventType.setEventTypeId(foundEventType.getEventTypeId());
				eventType.setEventType(newName);
				eventType.setBaseCost(newBaseCost);
				
				// open new session and commit
				openSession();
				
				session.beginTransaction();
				session.saveOrUpdate(foundEventType);
				session.getTransaction().commit();
				
				Hibernate.initialize(foundEventType);
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
				eventType.setEventType(oldName);
				eventType.setBaseCost(oldBaseCost);
			}
		}
		return valid ? eventType : null;
	}
	
	private void openSession() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	private void closeSession() {
		session.close();
	}

	/**
	 * Attempts to delete an EventType by <b>Id</b> from the database
	 * @param eventType the <code>EventType</code> to delete
	 * @return deleted <code>EventType</code> if found, <code>null</code> otherwise
	 */
	public EventType DeleteEventTypeById(EventType eventType) {
		boolean deleted = false;
		
		EventType foundEventType = null;
		// check if in database
		foundEventType = findEventType(eventType, true, false);
		
		if (foundEventType != null) {
			deleted = deleteEventType(foundEventType);
		}

		return deleted? foundEventType : null;
	}
	
	public EventType DeleteEventTypeByName(EventType eventType) {
		boolean deleted = false;
		
		EventType foundEventType = null;
		// check if in database
		foundEventType = findEventType(eventType, false, true);
		
		if (foundEventType != null) {
			deleted = deleteEventType(foundEventType);
			if (deleted) {
			}
		}

		return deleted? foundEventType : null;
	}

	// helper method to delete from database. returns success / failure status
	private boolean deleteEventType(EventType eventTypeToDelete) {
		boolean success = false;
		try {
			openSession();
			
			session.beginTransaction();
			session.delete(eventTypeToDelete);
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
