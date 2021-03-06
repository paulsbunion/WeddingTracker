package com.defrainphoto.weddingtracker.model;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
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
			
			// add a mileage db record
			Integer year = newEvent.getEventDate().getYear() + 1900;
			Mileage newMileage = new Mileage(newEvent.getEventId(), year, 0.0);
			
			openSession();
			session.beginTransaction();
			System.out.println("adding mileage");
			System.out.println(newMileage);
			session.save(newMileage);
			session.getTransaction().commit();
			
			closeSession();
			
			found = true;
		}
		
		return found ? newEvent : null;
	}
	
	public Event updateEvent(Event event) {
		
		boolean found = false;
		Event temp = findEvent(event, true, false);
		Integer oldYear = 0;
		Integer newYear = event.getEventDate().getYear() + 1900;
		
		// if not in DB, throw exception
		if (temp == null) {
			throw new EntityNotFoundException("Entity not Found:  " + event.toString());
		}
		
		// else, open session, save, and commit
		else {
			oldYear = temp.getEventDate().getYear() + 1900;
//			System.out.println("in the update");
			// check if the new event name already exists
			Event findEventName = findEvent(temp, false, true);
//			System.out.println("done finding event");
//			System.out.println(findEventName);
//			System.out.println("event ids:");
//			System.out.println(findEventName.getEventId() + " : " + temp.getEventId());
			
			openSession();
			
			session.beginTransaction();
			
			// if already in DB, throw exception
			if (findEventName != null && !findEventName.getEventId().equals(temp.getEventId())) {
//				System.out.println("throwing exception");
				throw new EntityExistsException("Entity already Exists:  " + findEventName.toString());
			}
//			System.out.println("the saved event type");
//			System.out.println(event.getEventType());
			session.saveOrUpdate(event);
			session.getTransaction().commit();
			
			Hibernate.initialize(event);
			closeSession();
			
			// if year changed, update year in mileage
			if (oldYear != newYear) {
				Mileage mileage = new Mileage(event.getEventId(), oldYear, 0.0);
				openSession();
				
				session.beginTransaction();
				mileage = (Mileage)session.get(Mileage.class, mileage.getEventId());
				System.out.println("old mileage");
				System.out.println(mileage);
				mileage.setYear(newYear);
				
				session.saveOrUpdate(mileage);
				System.out.println("new mileage");
				System.out.println(mileage);
				session.getTransaction().commit();
			}
			found = true;
		}
		
		return found ? event : null;
	}
	
	public List<Event> getAllEvents() {
		boolean found = false;
		List<Event> result = null;
		openSession();
		
		session.beginTransaction();
		StringBuilder queryString = new StringBuilder("from Event ev");
		queryString.append(" order by ev.eventDate");
		Query query = session.createQuery(queryString.toString());
		
		List list = query.list();
		
		session.getTransaction().commit();
		
		if (list != null && !list.isEmpty()) {
			result  = (List<Event>) list;
			found = true;
		}
		
		else {
			found = false;
		}
		Hibernate.initialize(list);
		closeSession();
		
		return found ? list : null;
	}
	
	public List<Event> getEventsByToday(Date today, boolean getPastEvents) {
		String EqualityOperator = ">=";
		if (getPastEvents) {
			EqualityOperator = "<";
		}
		boolean found = false;
		List<Event> result = null;
		openSession();
		session.beginTransaction();
		StringBuilder queryString = new StringBuilder("from Event ev");
		queryString.append(" where ev.eventDate " + EqualityOperator +" :today");
		queryString.append(" order by ev.eventDate");
		Query query = session.createQuery(queryString.toString());
		query.setDate("today", today);
		
		List list = query.list();
		
		session.getTransaction().commit();
		
		if (list != null && !list.isEmpty()) {
			result  = (List<Event>) list;
			found = true;
		}
		
		else {
			found = false;
		}
		Hibernate.initialize(list);
		closeSession();
		
		return found ? list : null;
	}
	
	public List<Event> getAllPastEvents(Date today) {
		boolean found = false;
		List<Event> result = null;
		openSession();
		session.beginTransaction();
		StringBuilder queryString = new StringBuilder("from Event ev");
		queryString.append(" where ev.eventDate <:today");
		queryString.append(" order by ev.eventDate");
		Query query = session.createQuery(queryString.toString());
		query.setDate("today", today);
		
		List list = query.list();
		
		session.getTransaction().commit();
		
		if (list != null && !list.isEmpty()) {
			result  = (List<Event>) list;
			found = true;
		}
		
		else {
			found = false;
		}
		Hibernate.initialize(list);
		closeSession();
		
		return found ? list : null;
	}
	
	public List<Event> getAllEventsByYear(String year) {
		boolean found = false;
		List<Event> result = null;
		openSession();
		session.beginTransaction();
		StringBuilder queryString = new StringBuilder("from Event ev");
		queryString.append(" where year(ev.eventDate) = :year");
		queryString.append(" order by ev.eventDate");
		Query query = session.createQuery(queryString.toString());
		query.setParameter("year", year);
		
		List list = query.list();
		
		session.getTransaction().commit();
		
		if (list != null && !list.isEmpty()) {
			result  = (List<Event>) list;
			found = true;
		}
		
		else {
			found = false;
		}
		Hibernate.initialize(list);
		closeSession();
		
		return found ? list : null;
	}
	
	public Event getEventById(Event event) {
		Event result;
		
		result = findEvent(event, true, false);
		return result; 
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
			event = temp;
			found = true;
		}
		
		else {
			found = false;
		}
		Hibernate.initialize(event);
		Hibernate.initialize(event.getEventType());
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
	
	public Event deleteEventById(Event event) {
		boolean deleted = false;
		
		Event foundEvent = null;
		// check if in database
		foundEvent = findEvent(event, true, false);
		
		if (foundEvent != null) {
			deleted = deleteEvent(foundEvent);
		}

		return deleted? foundEvent : null;
	}
	
	// helper method to delete from database. returns success / failure status
	private boolean deleteEvent(Event eventToDelete) {
		boolean success = false;
		try {
			openSession();
			
			session.beginTransaction();
			session.delete(eventToDelete);
			session.getTransaction().commit();
			closeSession();
			success = true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}
	
	private void openSession() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	public void closeSession() {
		session.close();
	}
}
