package com.defrainphoto.weddingtracker.model;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

public class EventPhotographerManager {
	private Session session;

	public List<EventPhotographer> getEventPhotographers(String eventId) {
		List<EventPhotographer> result = null;
		
		result = (List<EventPhotographer>) findEventPhotographers(eventId, true, false);
		
		return result;
	}
	
	public List<EventPhotographer> getPhotographerEvents(String staffId) {
		List<EventPhotographer> result = null;
		
		result = (List<EventPhotographer>) findEventPhotographers(staffId, false, true);
		
		return result;
	}
	
	private List findEventPhotographers(String id, boolean byEventID, boolean byPhotographerId) {
		List<EventPhotographer> eventPhotographers = null;
		boolean found = false;
		openSession();
		
		session.beginTransaction();
		
		StringBuilder queryString = new StringBuilder("from EventPhotographer where ");
		buildQuery(queryString, byEventID, byPhotographerId);
		
		Query query = session.createQuery(queryString.toString());
		setQueryVariables(id, query, byEventID, byPhotographerId);
		
		List list = query.list();
		
		session.getTransaction().commit();
		
		if (list != null && !list.isEmpty()) {
			List<EventPhotographer> temp = (List<EventPhotographer>) list;
			eventPhotographers = temp;
			found = true;
		}
		
		else {
			found = false;
		}
		Hibernate.initialize(eventPhotographers);
		closeSession();
		
		return found ? eventPhotographers : null;
	}
	
	private void setQueryVariables(String id, Query query, boolean byEventID, boolean byPhotographerId) {
		if (byEventID) {
			query.setParameter("eventId", id);
		}
		if (byPhotographerId) {
			query.setParameter("staffId", id);
		}
	}
	
	private void buildQuery(StringBuilder queryString, boolean byEventID, boolean byPhotographerId) {
		
		boolean moreThanOne = false;
		
		if (byEventID) {
			queryString.append("eventId = :eventId ");
			moreThanOne = true;
		}
		if (byPhotographerId) {
			if (moreThanOne) {
				queryString.append("AND ");
			}
			queryString.append("staffId = :staffId ");
			moreThanOne = true;			
		}
	}
	
	private void openSession() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	public void closeSession() {
		session.close();
	}
}
