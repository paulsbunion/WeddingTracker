package com.defrainphoto.weddingtracker.model;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityExistsException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class EventManager {
	private Session session;
	
	public Event addEvent(Event newEvent) {
		boolean found = false;
		Event temp = findEvent(newEvent, false, true);
		
		// if already in DB, throw exception
		if (temp != null) {
			throw new EntityExistsException("Entity already Exists:  " + newEvent.toString());
		}
		
		// else, open session, save, and commit
		else {
			openSession();
			
			session.beginTransaction();
			session.save(newEvent);
			session.getTransaction().commit();
			
			temp = findEvent(newEvent, false, true);
			newEvent.setEventId(temp.getEventId()); 
			Hibernate.initialize(newEvent);
			closeSession();
			found = true;
		}
		
		return found ? newEvent : null;
	}
	
	private Event findEvent(Event event, boolean byID, boolean byName) {
		boolean found = false;
		openSession();
		
		session.beginTransaction();
		
		StringBuilder queryString = new StringBuilder("from Event where ");
		buildQuery(queryString, byID, byName);
		
		Query query = session.createQuery(queryString.toString());
		setQueryVariables(event, query, byID, byName);
		
		List list = query.list();
		
		session.getTransaction().commit();
		
		if (list != null && !list.isEmpty()) {
			Event temp = (Event) list.get(0);
			event.setEventId(temp.getEventId());
			found = true;
		}
		
		else {
			found = false;
		}
		Hibernate.initialize(event);
		closeSession();
		
		return found ? event : null;
	}
	
	private void setQueryVariables(Event event, Query query, boolean byID, boolean byName) {
		if (byID) {
			query.setParameter("eventId", event.getEventId());
		}
		if (byName) {
			query.setParameter("name", event.getEventName());
		}
	}
	
	private void buildQuery(StringBuilder queryString, boolean byID, boolean byName) {
		
		boolean moreThanOne = false;
		
		if (byID) {
			queryString.append("eventId = :eventId ");
			moreThanOne = true;
		}
		if (byName) {
			if (moreThanOne) {
				queryString.append("AND ");
			}
			queryString.append("eventName = :name ");
			moreThanOne = true;			
		}
	}
//	public List<Event> getEventsByPhotographer(Photographer photographer) {
//		openSession();
//		try {
//		session.beginTransaction();
//		
//		StringBuilder queryString = new StringBuilder("from Event where ");
//		buildQuery(queryString, byEventId);
//		Query query = session.createQuery(queryString.toString());
//		setQueryVariables(timeline, query, byEventId);
//		}
//		
//		catch (HibernateException e) {
//			e.printStackTrace();
//			session.getTransaction().rollback();
//		}
//		
//		finally {
//			session.close();
//		}
//	}
	
//	public void addPhotographer(Event multiPhotogEvent, Photographer newPhotographer, char isMain) {
//		
//		// start a session
//		openSession();
//		
//		// start a transaction
//		session.beginTransaction();
//		
//		// save the photographer
//		session.saveOrUpdate(newPhotographer);
//		
//		// add photographer to the event
//		multiPhotogEvent.setPhotographers(newPhotographer);
//		
//		// save the event
//		session.saveOrUpdate(multiPhotogEvent);
//		session.getTransaction().commit();
//		
//		// close the session
//		closeSession();
//	}
//
//	public void addLocation(Event locationEvent, Location location) {
//		openSession();
//
//		session.beginTransaction();
//		
//		// save the location
//		session.saveOrUpdate(location);
//		
//		// add location to event
////		locationEvent.setLocations(location);
//		
//		// save the event
//		session.saveOrUpdate(locationEvent);
//		session.getTransaction().commit();
//		
//		// close the session
//		closeSession();
//	}
	
	public void addClient() {
		
	}
	
//	public Set<Photographer> getPhotographers() {
//		return null;
//	}
	
	public void getTimeline() {
		
	}
	
	public void setTimeline() {
		
	}
	
	public void updateExtraPrice() {
		
	}
	
	public void getDuration() {
		
	}
	
	public void getStartTime() {
		
	}
	
	private void openSession() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	public void closeSession() {
		session.close();
	}
}
