package main.java.com.defrainphoto.weddingtracker.model;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class PhotographerManager {
	public void addPhotographer(Photographer newPhotographer, Session session) {
		System.out.println("blober");
		boolean found = false;
		
		// check if already in set
		session.beginTransaction();
		System.out.println("blober2");
		Query query = session.createQuery("from Photographer where firstName = :fname AND lastName = :lname");
		query.setParameter("fname", newPhotographer.getFirstName());
		query.setParameter("lname", newPhotographer.getLastName());
		List list = query.list();
		System.out.println("blober3");
		if (list == null || list.isEmpty()) {
			System.out.println("start here");
			newPhotographer.setStaffId(null);
			System.out.println("and here");
			session.saveOrUpdate(newPhotographer);
		}
		
		else {
			System.out.println("this here");
			Photographer temp = (Photographer) list.get(0);
			System.out.println("data: " + temp);
			newPhotographer.setStaffId("" + temp.getStaffId());
			System.out.println("here again: " + temp.getStaffId());
			if (!temp.getClass().isInstance(newPhotographer)) {
				System.out.println("not equal, updating");
				System.out.println(newPhotographer.getStaffId());
				System.out.println(temp.getStaffId());
				
				System.out.println(newPhotographer.getFirstName());
				System.out.println(temp.getFirstName());
				
				System.out.println(newPhotographer.getLastName());
				System.out.println(temp.getLastName());
				
				System.out.println(newPhotographer.hashCode());
				System.out.println(temp.hashCode());
				
				System.out.println(newPhotographer.getClass());
				System.out.println(temp.getClass());
				
				
				System.out.println(newPhotographer);
				System.out.println(temp);
				session.saveOrUpdate(newPhotographer);
			}
			System.out.println("saved");
		}
		
		session.getTransaction().commit();
	}

	public Photographer getPhotographers(Photographer photog, Session session) {
		System.out.println("in this thing");
		boolean found = false;
		
		session.beginTransaction();
		
		Query query = session.createQuery("from Photographer where firstName = :fname" + " AND lastName = :lname");
		query.setParameter("fname", photog.getFirstName());
		query.setParameter("lname", photog.getLastName());
		List list = query.list();
		
		if (list != null && !list.isEmpty()) {
			System.out.println("we are here");
			Photographer temp = (Photographer) list.get(0);
			photog.setStaffId(temp.getStaffId());
//			photog.setStaffId("" + ((Object[])list.get(0))[0]);
			found = true;
		}
		else {
			System.out.println("yep, here");
			found = false;
		}
		
		session.getTransaction().commit();
		System.out.println("i'm ready to retrun");
		return found ? photog : null; 
	}
}
