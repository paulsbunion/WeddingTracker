package com.defrainphoto.weddingtracker.model;

import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class EventManager {
	private Session session;
	
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
