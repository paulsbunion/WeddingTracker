package test.java.com.defrainphoto.weddingtracker.test;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.java.com.defrainphoto.weddingtracker.model.HibernateUtil;
import main.java.com.defrainphoto.weddingtracker.model.Photographer;

public class PhotographerTest {

	static Session session;
	static List<Photographer> photographers;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		photographers = session.createQuery("from Photographer ORDER BY name", Photographer.class).list();
		session.getTransaction().commit();
	}
	
	@Before
	public void setUp() {
		//session.beginTransaction();
	}
	
	@After
	public void tearDown() {
		//session.getTransaction().commit();
	}
	
	@Test
	public void testGetUser_id() {
		boolean found = false;
		for (Photographer p : photographers) {
			if (p.getName().equals("Mertle Smith")) {
				found = true;
				break;
			}
		}
		assertTrue("Mertle Smith not found", found);
	}

	@Test
	public void testSetUser_id() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAddress() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetAddress() {
		fail("Not yet implemented");
	}

}
