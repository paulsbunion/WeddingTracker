package test.java.com.defrainphoto.weddingtracker.test;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.com.defrainphoto.weddingtracker.model.HibernateUtil;
import main.java.com.defrainphoto.weddingtracker.model.Photographer;
import main.java.com.defrainphoto.weddingtracker.model.PhotographerManager;

public class PhotographerManagerTest {

	Session session;
	
	Photographer photog;
	PhotographerManager photogManager;
	
	@Before
	public void setUp() {
		session = HibernateUtil.getSessionFactory().openSession();
		
		photog = new Photographer(null, "John", "Smith");
		photogManager = new PhotographerManager();
	}
	
	@After
	public void tearDown() {
		session.close();
	}
	
	@Test
	public void testInsertPhotographer() {
		System.out.println("ready to retrun");
		photogManager.addPhotographer(photog, session);
		System.out.println("ready to retrun");
		
		Photographer retreivedPhotog = photogManager.getPhotographers(photog, session);
		
		assertEquals("photographer not found", photog, retreivedPhotog);
	}
	
	@Test
	public void testUpdatePhotographer() {
		photog.setFirstName("no longer John");
		
//		session.beginTransaction();
		photogManager.addPhotographer(photog, session);
//		session.getTransaction().commit();
		
//		session.beginTransaction();
		Photographer newPhotog = session.load(Photographer.class, photog.getStaffId());
//		session.getTransaction().commit();
		
		assertEquals("not updated", photog.getFirstName(), newPhotog.getFirstName());
	}

}
