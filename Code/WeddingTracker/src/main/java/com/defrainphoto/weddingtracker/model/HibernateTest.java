package main.java.com.defrainphoto.weddingtracker.model;

import org.hibernate.Session;

public class HibernateTest {
	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		session.beginTransaction();
		Photographer photographer = new Photographer();
		photographer.setUser_id("12345");
		photographer.setName("Mertle Smith");
		photographer.setAddress("1234 Main St, Columbus, OH, 43062");
		
		session.saveOrUpdate(photographer);
		session.getTransaction().commit();
	}
}
