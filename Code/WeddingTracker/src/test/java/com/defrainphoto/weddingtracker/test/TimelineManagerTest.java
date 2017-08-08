package test.java.com.defrainphoto.weddingtracker.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.event.DocumentEvent.EventType;

import org.hibernate.Session;
import org.hibernate.type.ListType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.com.defrainphoto.weddingtracker.model.Event;
import main.java.com.defrainphoto.weddingtracker.model.EventManager;
import main.java.com.defrainphoto.weddingtracker.model.HibernateUtil;
import main.java.com.defrainphoto.weddingtracker.model.TimeChunk;
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
		createTimeline();
		createTimeChunks();
	}

	@After
	public void tearDown() {
		deleteEvent();
		deleteTimeline();
		deleteTimeChunks();
	}
	
	@Test
	public void testAddTimeline() {
		List<Timeline> timelineList = new LinkedList<Timeline>();
		date = new Date(2017, 06, 15);
		startTime = new Time(0, 0, 0);
		duration =  new Time(0, 0, 0);
		extraCost = "";
		
		System.out.println("Adding a timeline");
		
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
	
	@Test
	public void testAddTimechunk() {
		Time sTime = new Time(1, 0, 0);
		Time dur = new Time(0, 30, 0);
//		session.close();
		
		System.out.println(" the error begins here");
		System.out.println("timeline: " );
		System.out.println(timeline.toString());
//		 //create a timechunk
		TimeChunk addedChunk = new TimeChunk("4", timeline, 1, sTime, null, dur, "first chunk", null, null);
		System.out.println("the timeline: " + timeline.toString());
		timelineManager.addTimechunk(timeline, addedChunk);
//		TimeChunk addedChunk = timelineManager.timeChunkManager.addTimeChunk(new TimeChunk("4", 
//				timeline, 1, sTime, null, dur, "first chunk", null, null));
		System.out.println("the added chunk");
		System.out.println(addedChunk);
		TimeChunk foundChunk = timelineManager.timeChunkManager.getTimeChunkByIdAndTimeline(addedChunk);
		System.out.println("the found chunk");
		System.out.println(foundChunk);
		// ensure added
		assertEquals("did not find the added chunk: ", addedChunk, foundChunk);
	}
	
	
	@Test
	public void testGetTimeChunks() {
		// event
		
		Set<TimeChunk> datatest = timelineManager.getTimeChunks(timeline);
		System.out.println("timeline size: " + datatest.toString());
		ArrayList<TimeChunk> data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		assertEquals("should have 3 chunks", 3, data.size());
		for (int i = 0; i < data.size(); i++) {
			assertEquals("invalid chunk id",data.get(i).getChunkId() , "" + (i + 1));
		}
	}
	
	
	@Test
	public void testSetTimeChunks() {
		Set<TimeChunk> chunksToAdd = new HashSet<TimeChunk>();
		
		// create a set of timechunks
		Time sTime = new Time(1, 0, 0);
		Time dur = new Time(0, 30, 0);
		
		//create a timechunk
		TimeChunk addedChunk = new TimeChunk("1", 
				timeline, 1, sTime, null, dur, "group chunk 1", null, null);
		chunksToAdd.add(addedChunk);
		
		addedChunk = new TimeChunk("2", 
				timeline, 1, sTime, null, dur, "group chunk 2", null, null);
		chunksToAdd.add(addedChunk);
		
		addedChunk = new TimeChunk("3", 
				timeline, 1, sTime, null, dur, "group chunk 3", null, null);
		chunksToAdd.add(addedChunk);
		
		addedChunk = new TimeChunk("4", 
				timeline, 1, sTime, null, dur, "group chunk 4", null, null);
		chunksToAdd.add(addedChunk);
		
		// add all chunks
		timelineManager.setTimeChunks(timeline, chunksToAdd);
		
		// get the addeed chunks
		ArrayList<TimeChunk> data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		for (int i = 0; i < data.size(); i++) {
			assertEquals("invalid chunk data",data.get(i).getDescription() , "group chunk " + (i + 1));
		}
	}
	
	
//	@Test
//	public void testRemoveTimechunk() {
//		
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
		}
		
		catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Could not create object");
		}
		
		finally {
			closeSession();
		}
		
	}
	
	private void createTimeline() {		
		//create a timeline
		startTime = new Time(11, 0, 0);
		duration = new Time(0, 0, 0);
		timeline = new Timeline(event.getEventId(), event, null, startTime, duration);
		try {
			openSession();
			System.out.println("creating a timeline");
			session.beginTransaction();
			session.createSQLQuery("insert into timeline(eventid, starttime)" + 
					" values(1, '12:30:00')").executeUpdate();
			session.getTransaction().commit();
			closeSession();
		}
		catch (Exception e) {}
		finally {
			closeSession();
		}
	}
	
	private void createTimeChunks() {
		try {
			openSession();
			
			System.out.println("creating time chunks");
			session.beginTransaction();
			session.createSQLQuery("insert into time_chunk(chunkid, eventid, position, starttime, duration)" + 
					" values(1, 1, 1, '12:30:00', '00:20:00')").executeUpdate();
			session.createSQLQuery("insert into time_chunk(chunkid, eventid, position, starttime, duration)" + 
					" values(2, 1, 2, '13:00:00', '00:30:00')").executeUpdate();
			session.createSQLQuery("insert into time_chunk(chunkid, eventid, position, starttime, duration)" + 
					" values(3, 1, 3, '13:30:00', '00:45:00')").executeUpdate();
			System.out.println("done creating chunks");
			session.getTransaction().commit();
		}
		catch (Exception e) {}
		finally {
			closeSession();
		}
	}
	
	private void deleteEvent() {
		openSession();
		
		session.beginTransaction();
		session.createQuery("delete from " + Event.class.getName()).executeUpdate();
		System.out.println("Name: " + Event.class.getName());
		session.getTransaction().commit();
		
		closeSession();
	}

	private void deleteTimeline() {
		openSession();
		
		session.beginTransaction();
		session.createQuery("delete from " + Timeline.class.getName()).executeUpdate();
		session.getTransaction().commit();

		closeSession();		
	}

	private void deleteTimeChunks() {
		openSession();
		
		session.beginTransaction();
		session.createQuery("delete from " + TimeChunk.class.getName()).executeUpdate();
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
