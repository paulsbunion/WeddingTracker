package test.java.com.defrainphoto.weddingtracker.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.DocumentEvent.EventType;

import org.hibernate.Session;
import org.hibernate.type.ListType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.com.defrainphoto.weddingtracker.model.Client;
import main.java.com.defrainphoto.weddingtracker.model.Event;
import main.java.com.defrainphoto.weddingtracker.model.EventManager;
import main.java.com.defrainphoto.weddingtracker.model.HibernateUtil;
import main.java.com.defrainphoto.weddingtracker.model.TimeChunk;
import main.java.com.defrainphoto.weddingtracker.model.TimeChunkManager;
import main.java.com.defrainphoto.weddingtracker.model.Timeline;
import main.java.com.defrainphoto.weddingtracker.model.TimelineManager;

public class TimelineManagerTest {
	TimelineManager timelineManager;
	EventManager eventManager;
	
	Timeline timeline;
	Event event;
	TimeChunk timeChunk;
	main.java.com.defrainphoto.weddingtracker.model.EventType eventType;
	Date date;
	Time startTime;
	Time duration;
	String extraCost;
	
	Session session;
	boolean doOnce = false;
	
	@Before
	public void setUp() {
		timelineManager = new TimelineManager();
		createEvent();
		createTimeChunks();
	}

	@After
	public void tearDown() {
//		deleteEvent();
//		deleteTimeChunks();
	}
	
	@Test
	public void testAddTimeline() {
		List<Timeline> timelineList = new LinkedList<Timeline>();
		date = new Date(2017, 06, 15);
		startTime = new Time(0, 0, 0);
		duration =  new Time(0, 0, 0);
		extraCost = "";
		
		try {
			event = new Event("2", "the 2nd big Event", eventType, date, startTime, duration, null, "n", extraCost, "", "n", null, null);
			System.out.println(event.toString());
			openSession();
			session.beginTransaction();
			session.save(event);
			session.getTransaction().commit();
			closeSession();
			
			Time startTime = new Time(12, 30, 0);
			timeline = new Timeline(event.getEventId(), event, null, startTime, duration);
			timelineList.add(timeline);
			System.out.println("getting ready to add a timeline");
			System.out.println(event.toString());
			System.out.println(timeline.toString());
			timelineManager.addTimeline(timeline);
			System.out.println("Timeline added!");
			
			Timeline retreived;
			retreived = timelineManager.getTimelineByEventId(timeline);
			
			assertEquals("did not add Event", timeline, retreived);
		}
		catch(Exception e) {
			System.out.println("ERROR!!");
			System.out.println(e.getMessage());
		}
		
		// clean up
//		cleanUp(timelineList);
	}
	
//	@Test
//	public void testGetTimeChunks() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testSetTimeChunks() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testAddTimechunk() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testRemoveTimechunk() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testGetTotalTime() {
//		fail("Not yet implemented");
//	}
	
	
//	@Test
//	public void testAddTimeChunk() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testGetChunkByDescriptionAndTimeline() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testGetChunkByIdAndTimeline() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testSetPhotographer() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testSetClient() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testSetDescription() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testSetDuration() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testSetLocation() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testSetPosition() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testSetStartTime() {
//		fail("Not yet implemented");
//	}
	
	private void cleanUp(List<Timeline> timelineList) {
		// TODO Auto-generated method stub
		Iterator<Timeline> timelineIterator = timelineList.iterator();
		Timeline temp;
		System.out.println("deleting a timeline");
		while (timelineIterator.hasNext()) {
			temp = timelineIterator.next();
			try {
				System.out.println("deleting a timeline");
				timelineManager.DeleteTimelineByEventId(temp);
				System.out.println("success deleting");
			}
			catch (Exception e) {
				// do nothing
			}
		}
	}
	
	private void createEvent() {
		Date date = new Date(2017, 10, 5);
		Time startTime = new Time(12, 30, 0);
		Time duration = new Time(0, 0, 0);
		String extraCost = "";
		
		 eventType = new main.java.com.defrainphoto.weddingtracker.model.EventType
				("1", "basic Event", "$50");
		event = new Event("1", "the big Event", eventType, date, startTime, duration, null, "n", extraCost, "", "n", null, null);
		
		
		try {
			
			openSession();

			System.out.println("Creating an eventType!");
			session.beginTransaction();
//			session.createSQLQuery("insert into eventtype(eventtypeid, eventtype, basecost)" + 
//			" values(1, basic event, $100)");
			session.saveOrUpdate(eventType);
			session.getTransaction().commit();
			
			System.out.println("EventType Created!");
			System.out.println("Creating an event!");
			session.beginTransaction();
//			session.createSQLQuery("insert into Event(eventid, eventname, type, eventdate, starttime, notes)" +
//			" values(1, the big event, " + date.toString() + ", 12:30:00, the photog will be late)");
			session.saveOrUpdate(event);
			session.getTransaction().commit();
			System.out.println("Event Created!");
			
			closeSession();
		}
		
		catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Could not create object");
		}
		
	}
	
	private void createTimeChunks() {
		openSession();
		System.out.println("creating time chunks");
		session.beginTransaction();
		session.createSQLQuery("insert into time_chunk(chunkid, eventid, position, starttime)" + 
				" values(1, 2, 1, '12:30:00')").executeUpdate();
		session.createSQLQuery("insert into time_chunk(chunkid, eventid, position, starttime)" + 
				" values(2, 2, 2, '13:00:00')").executeUpdate();
		session.createSQLQuery("insert into time_chunk(chunkid, eventid, position, starttime)" + 
				" values(3, 2, 3, '13:30:00')").executeUpdate();
		System.out.println("done creating chunks");
		session.getTransaction().commit();
		
		closeSession();
	}
	
	private void deleteEvent() {
		openSession();
		
		session.beginTransaction();
		session.createQuery("delete from " + Event.class.getName()).executeUpdate();
		System.out.println("Name: " + Event.class.getName());
		session.getTransaction().commit();
		
//		session.beginTransaction();
//		session.createQuery("delete from " + EventType.class.getName());
//		session.getTransaction().commit();
		
		closeSession();
	}


	private void deleteTimeChunks() {
		openSession();
		
		session.beginTransaction();
		session.createQuery("delete from " + TimeChunk.class.getName());
		session.getTransaction().commit();

		closeSession();		
	}
	
	private void openSession() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	private void closeSession() {
		session.close();
	}

}
