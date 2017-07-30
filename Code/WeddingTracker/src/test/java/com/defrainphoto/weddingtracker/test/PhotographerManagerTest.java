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
		//photog = new Photographer(null, "John", "Smith");
		photogManager = new PhotographerManager();
	}
	
	@After
	public void tearDown() {
//		session.close();
	}
	
	@Test
	public void testInsertPhotographer() {
		try {
			photog = new Photographer(null, "John", "Smith");
			photogManager.addPhotographer(photog);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Photographer retreivedPhotog = photogManager.getPhotographerByName(photog);
		assertEquals("photographer not added", photog, retreivedPhotog);
		// clean up
		photogManager.DeletePhotographerById(photog);
	}
	
	@Test
	public void testUpdatePhotographerFirstName() {
		try {
			photog = new Photographer(null, "John", "Smith");
			photogManager.addPhotographer(photog);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Photographer updatedPhotog = null;

		try {
			updatedPhotog = photogManager.setPhotographerFirstName(photog, "no longer John");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Photographer newPhotog = photogManager.getPhotographerById(photog);
		
		assertTrue("Did not update First Name", updatedPhotog != null);
		assertEquals("Entities are not the same" + photog.toString() + " vs. " + newPhotog.toString(),
				photog.getFirstName(), newPhotog.getFirstName());
		// clean up
		photogManager.DeletePhotographerById(newPhotog);
	}
	
	@Test
	public void testUpdatePhotographerLastName() {
		photog = new Photographer(null, "John", "Smith");
		try {
			photogManager.addPhotographer(photog);
		}
		catch (Exception e) {
			
		}
		
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
		// clean up
		photogManager.DeletePhotographerById(newPhotog);
	}
	
	@Test
	public void testDeletePhotographerById() {
		Photographer deleteMe1 = new Photographer(null, "me1", "last1");
		photogManager.addPhotographer(deleteMe1);
		
		Photographer deletedPhotographer = null;
		try {
			deleteMe1.setStaffId(photogManager.getPhotographerByName(deleteMe1).getStaffId());
			deletedPhotographer = photogManager.DeletePhotographerById(deleteMe1);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		
		// try to get deleted photographer
		Photographer newPhotog = photogManager.getPhotographerById(deleteMe1);
		
		assertTrue("Did not Delete Photographer ", newPhotog == null);
		assertEquals("Wrong return from delete", deleteMe1, deletedPhotographer);
	}
	
	@Test
	public void testDeletePhotographerByName() {

		Photographer deleteMe1 = new Photographer(null, "FirstName", "lastName");
		Photographer deleteMe2 = new Photographer(null, "SecondFirstName", "SecondLastName");
		
		deleteMe1 = photogManager.addPhotographer(deleteMe1);
		if (deleteMe1 != null) {
			System.out.println("HES ALIVET!!!!  " + deleteMe1.toString());
		}
		photogManager.addPhotographer(deleteMe2);
		
		Photographer deletedPhotographer = null;
		Photographer deletedPhotographer2 = null;
		
//		Photographer tempPhotog = new Photographer(null, "no Longer John", "Smith");
//		Photographer tempPhotog2 = new Photographer(null, "John", "no Longer Smith");
		
		try {
			deletedPhotographer = photogManager.DeletePhotographerByName(deleteMe1);
			System.out.println("for shiz, the deleted photog was " + deletedPhotographer.toString());
			deleteMe1.setStaffId(deletedPhotographer.getStaffId());
			
			deletedPhotographer2 = photogManager.DeletePhotographerByName(deleteMe2);
			deleteMe2.setStaffId(deletedPhotographer2.getStaffId());
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		
		//try to get deleted photographers
		Photographer newPhotog = photogManager.getPhotographerByName(deleteMe1);
		Photographer newPhotog2 = photogManager.getPhotographerByName(deleteMe2);
		
		assertNull("Did not Delete Photographer ", newPhotog);
		assertNull("Did not Delete Photographer ", newPhotog2);
		assertEquals("Wrong return from delete", deleteMe1, deletedPhotographer);
		assertEquals("Wrong return from delete", deleteMe2, deletedPhotographer2);
	}

}
