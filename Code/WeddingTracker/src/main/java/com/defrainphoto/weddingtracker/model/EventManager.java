package main.java.com.defrainphoto.weddingtracker.model;

import org.hibernate.Session;

public class EventManager {
	private Session session;
	
	public void addPhotographer(Event multiPhotogEvent, Photographer newPhotographer, char isMain) {
		
		// start a session
		openSession();
		
		// start a transaction
		session.beginTransaction();
		
		// save the photographer
		session.saveOrUpdate(newPhotographer);
		
		// add photographer to the event
		multiPhotogEvent.setPhotographers(newPhotographer);
		
		// save the event
		session.saveOrUpdate(multiPhotogEvent);
		session.getTransaction().commit();
		
		// close the session
		closeSession();
	}

	public void addLocation(Event locationEvent, Location location) {
		openSession();
		if (session == null) {
			System.out.println("NULL IN HERE!!!");
		}
		session.beginTransaction();
		
		// save the location
		session.saveOrUpdate(location);
		
		// add location to event
//		locationEvent.setLocations(location);
		
		// save the event
		session.saveOrUpdate(locationEvent);
		session.getTransaction().commit();
		
		// close the session
		closeSession();
	}
	
	public void openSession() {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		//if (session == null || !session.isConnected()) {
			session = HibernateUtil.getSessionFactory().openSession();
		//}		
	}
	
	public void closeSession() {
		session.close();
	}
}