package futureTESTS;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Set;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.com.defrainphoto.weddingtracker.model.Event;
import main.java.com.defrainphoto.weddingtracker.model.EventManager;
import main.java.com.defrainphoto.weddingtracker.model.HibernateUtil;
import main.java.com.defrainphoto.weddingtracker.model.Location;
import main.java.com.defrainphoto.weddingtracker.model.Photographer;

public class EventManagerTest {
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
	public void testCreateWeddingEvent() {
		Event weddingEvent = new Event();
		fail("Not yet implemented");
	}

	@Test
	public void testCreateMiniSessionEvent() {
		Event miniSession = new Event();
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetLocation() {
		Event locationEvent = new Event();
		Location location = new Location("123 Main St, Columbus, OH, 43147");
		
		eventManager.addLocation(locationEvent, location);
		
		fail("Not yet implemented");
	}
	
	@Test
	public void testmMultiplePhotographers() {
		// create base event
		Event multiPhotogEvent = new Event();
		multiPhotogEvent.setMultiplePhotographers(true);
		multiPhotogEvent.setId(100);

		multiPhotogEvent.setDescription("multi photographer event");
		
		// August 18, 2017 @ 5pm
		Date eventDay = new Date(2017, 8, 18, 17, 0);
		multiPhotogEvent.setEventDate(eventDay);
		
		// home address
		Location home = new Location("Home");
		multiPhotogEvent.setLocations(home);
		
		// multiple photographers = true
		multiPhotogEvent.setMultiplePhotographers(true);
		
		// create photographers
		Photographer p1 = new Photographer();
		Photographer p2 = new Photographer();
		
		p1.setName("name1");
		p1.setAddress("address2");
		p1.setUser_id("1");

		p2.setName("name2");
		p2.setAddress("address2");
		p2.setUser_id("2");
		
		// add photographers to DB
		session.beginTransaction();
		session.save(p1);
		session.save(p2);
		
		// commit changes
		session.getTransaction().commit();
		
		// add the photogs
		eventManager.addPhotographer(multiPhotogEvent, p1, 'Y');
		eventManager.addPhotographer(multiPhotogEvent, p2, 'N');
		
		// test getting photogs
		session.beginTransaction();
		// get the event
		Event loadedEvent = (Event) session.load(Event.class, 100);
		session.getTransaction().commit();
		
		//get the photogs
		Set<Photographer> eventPhotographers = loadedEvent.getPhotographers();
		
		// compare p1, p2 to event photogs
		assertTrue("p1 photographer not found in event", eventPhotographers.contains(p1));
		assertTrue("p2 photographer not found in event", eventPhotographers.contains(p1));
		
		// remove the event and photographers
		session.beginTransaction();
		session.remove(p1);
		session.remove(p2);
		session.remove(multiPhotogEvent);
		session.getTransaction().commit();
	}
}
