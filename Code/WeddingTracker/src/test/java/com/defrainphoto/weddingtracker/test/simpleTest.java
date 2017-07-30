package test.java.com.defrainphoto.weddingtracker.test;

import static org.junit.Assert.*;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.com.defrainphoto.weddingtracker.model.Client;
import main.java.com.defrainphoto.weddingtracker.model.Event;
import main.java.com.defrainphoto.weddingtracker.model.EventManager;
import main.java.com.defrainphoto.weddingtracker.model.EventType;
import main.java.com.defrainphoto.weddingtracker.model.HibernateUtil;
//import main.java.com.defrainphoto.weddingtracker.model.Location;
import main.java.com.defrainphoto.weddingtracker.model.Photographer;

public class simpleTest {
	// Hibernate session
	Session session;
	EventManager eventManager;
	
	@Before
	public void setUp() {
		// get an event manager for each test
		eventManager = new EventManager();
		// database
		session = HibernateUtil.getSessionFactory().openSession(); 
	}
	
	@After
	public void tearDown() {
		// terminate the session
		session.close();
	}
		
	@Test
	public void testAddSuccess() {
		assertEquals("Match", 5, 2 + 3);
	}
	
	@Test
	public void testAddFailure() {
		assertNotEquals("5 != " + (2+2), 5, 2 + 2);
	}
	
//	@Test
//	public void testGetLocation() {
//		Event locationEvent = new Event();
//		Location location = new Location(100, "Columbus", "OH", "43147", "123 E Main St.", "the loft");
//		
//		eventManager.addLocation(locationEvent, location);
//		
//		fail("Not yet implemented");
//	}
	@Test
	public void testAddEvent() {
		Client c = new Client("2", "jim", "Walker", "no address", "noPhone", "g@yahoo.com", "N");
		Client c2 = new Client("3", "Kevin", "Bean", "no address", "noPhone", "g@yahoo.com", "N");
		c.setClientId("1");
		c2.setClientId("2");
		
		Photographer p = new Photographer();
		p.setFirstName("home");
		p.setLastName("me");
		p.setStaffId("1");
		
		Photographer p2 = new Photographer();
		p2.setFirstName("christy");
		p2.setLastName("D");
		p2.setStaffId("2");
		
		//EventType simpleEventType = new EventType(1, "simpleEvent", "$50");
		EventType simpleEventType = new EventType();
		simpleEventType.setBaseCost("$50");
		simpleEventType.setEventType("simpleEvent");
		simpleEventType.setEventTypeId(1);
	
		session.beginTransaction();
		session.saveOrUpdate(c);
		session.saveOrUpdate(c2);
		session.saveOrUpdate(p);
		session.saveOrUpdate(p2);
		session.saveOrUpdate(simpleEventType);
		session.getTransaction().commit();
		
		session.beginTransaction();
		session.saveOrUpdate(p2);
		session.getTransaction().commit();
		
		Date day = new Date(2017, 12, 18,17,0);
		Time startTime = new Time(12, 0, 0);
		
		Event testEvent = new Event();
		testEvent.setEventId(1);
		testEvent.setDescription("test");
		testEvent.setAdditionalCost("$4.00");
		testEvent.setClients(c);
		testEvent.setClients(c2);
		testEvent.setEventDate(day);
		testEvent.setMultiClient("N");
		testEvent.setMultiplePhotographers("Y");
		testEvent.setPhotographers(p);
//		testEvent.setPhotographers(p2);
		testEvent.setEventType(simpleEventType);
		testEvent.setStartTime(startTime);
		
		session.beginTransaction();
		session.saveOrUpdate(testEvent);
//		session.flush();
		session.getTransaction().commit();
		
	}
	
	@Test
	public void testGetPhotographer() {
		String first = "christy";
		String last = "D";
		
		session.beginTransaction();
		SQLQuery query;
//		query = session.createSQLQuery("Select * from Photographer where firstName = :fname AND lastName = :lname");
//		query.setParameter("fname", first);
//		query.setParameter("lname", last);
		
		query = session.createSQLQuery("SELECT staffid, firstname, lastname from Photographer where firstName = 'christy' AND lastName = 'D'");
		//query.addEntity(Photographer.class);
		
		try {
			Photographer temp;
			
			List listPhotographers = query.list();
			for (ListIterator iter = listPhotographers.listIterator(); iter.hasNext();) {
//			for (Photographer p : listPhotographers) {
//				System.out.println("before 1");
				Object [] row = (Object[]) iter.next();
//				System.out.println("after 1");
//				int size = row.length;
//				System.out.println("length: " + size);
//				System.out.println("data[0]: " + row[0]);
//				System.out.println("data[1]: " + row[1]);
//				System.out.println("data[2]: " + row[2]);
				temp = new Photographer();
				//String data1 = (String) row[0];
//				System.out.println("after the cast");
				temp.setStaffId( "" + row[0]);
//				System.out.println("after first cast");
				temp.setFirstName("" + row[1]);
				temp.setLastName("" + row[2]);
				
				System.out.println("STAFF ID!!!!: " + temp.getStaffId());
			}
		}
		catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		
		
		
//		String pID = "not found";
//		List<Photographer> results;
//		results = (List<Photographer>) query.list();
////		for (Photographer p : results) {
////			//Photographer p = (Photographer) o;
////			pID = p.getStaffId();
////			System.out.println("PID: " + pID);
////		}
		System.out.println("HERE!!!!!!!!!!!!!!!!!\n");
		session.getTransaction().commit();
	}
}
