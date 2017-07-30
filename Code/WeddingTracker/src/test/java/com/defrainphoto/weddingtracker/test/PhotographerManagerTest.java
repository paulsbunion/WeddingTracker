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
		photog = new Photographer(null, "John", "Smith");
		photogManager = new PhotographerManager();
	}
	
	@After
	public void tearDown() {
//		session.close();
	}
	
	@Test
	public void testInsertPhotographer() {
		System.out.println("adding new photog");
		photogManager.addPhotographer(photog);
		System.out.println("done adding new photog");
		
		Photographer retreivedPhotog = photogManager.getPhotographerbyName(photog);
		System.out.println("added staff id: " + photog.getStaffId());
		assertEquals("photographer not added", photog, retreivedPhotog);
	}
	
	@Test
	public void testUpdatePhotographerFirstName() {
		Photographer updatedPhotog = null;

		try {
			updatedPhotog = photogManager.setPhotographerFirstName(photog, "no longer John");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		Photographer newPhotog = photogManager.getPhotographerById(photog);
		
		assertTrue("Did not update First Name", updatedPhotog != null);
		assertEquals("Entities are not the same" + photog.toString() + " vs. " + newPhotog.toString(),
				photog.getFirstName(), newPhotog.getFirstName());
	}
	
	@Test
	public void testUpdatePhotographerLastName() {
		Photographer updatedPhotog = null;
		try {
			updatedPhotog = photogManager.setPhotographerLastName(photog, "no longer Smith");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		Photographer newPhotog = photogManager.getPhotographerById(photog);
		
		assertTrue("Did not update Last Name", updatedPhotog != null);
		assertEquals("Entities are not the same: " + photog.toString() + " vs. " + newPhotog.toString(),
				photog.getLastName(), newPhotog.getLastName());
	}

}
