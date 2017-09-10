package com.defrainphoto.weddingtracker.test;

import static org.junit.Assert.*;

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
import javax.validation.constraints.AssertTrue;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.type.ListType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.defrainphoto.weddingtracker.model.Client;
import com.defrainphoto.weddingtracker.model.ClientManager;
import com.defrainphoto.weddingtracker.model.Event;
import com.defrainphoto.weddingtracker.model.EventManager;
import com.defrainphoto.weddingtracker.model.HibernateUtil;
import com.defrainphoto.weddingtracker.model.Location;
import com.defrainphoto.weddingtracker.model.Photographer;
import com.defrainphoto.weddingtracker.model.PhotographerManager;
import com.defrainphoto.weddingtracker.model.TimeChunk;
import com.defrainphoto.weddingtracker.model.Timeline;
import com.defrainphoto.weddingtracker.model.TimelineManager;

public class TimelineManagerTest {
	TimelineManager timelineManager;
	EventManager eventManager;
	ClientManager clientManager;
	PhotographerManager photogManager;
	
	Timeline timeline;
	Event event;
	TimeChunk timeChunk;
	com.defrainphoto.weddingtracker.model.EventType eventType;
	Date date;
	Time startTime;
//	Integer startTimeHr;
//	Integer startTimeMn;
	Time duration;
	String extraCost;
	
	Session session;
	
	@Before
	public void setUp() {
		timelineManager = new TimelineManager();
		clientManager = new ClientManager();
		photogManager = new PhotographerManager();
		createEventType();
		createEvent();
		createTimeline();
		createTimeChunks();
	}

	@After
	public void tearDown() {
		deleteEvent();
		deleteEventType();
		deleteTimeline();
		deleteTimeChunks();
		deleteClients();
		deletePhotographers();
	}
	
	@Test
	public void testAddTimeline() {
		List<Timeline> timelineList = new LinkedList<Timeline>();
		date = new Date(2017, 06, 15);
		startTime = Time.valueOf("0:0:0");
//		startTimeHr = 0;
//		startTimeMn = 0;
		duration =  Time.valueOf("0:0:0");
		extraCost = "";
		
		event = new Event("2", "the 2nd big Event", eventType, date, startTime, duration, null, extraCost, "", null, null);
		openSession();
		session.beginTransaction();
		session.save(event);
		session.getTransaction().commit();
		closeSession();
		
		Time startTime = new Time(12, 30, 0);
		timeline = new Timeline(event.getEventId(), event, null, startTime, duration);
		timelineList.add(timeline);
		timelineManager.addTimeline(timeline);
		
		Timeline retreived;
		retreived = timelineManager.getTimelineByEventId(timeline);
		
		assertEquals("did not add Event", timeline, retreived);
		
		// clean up
//		cleanUp(timelineList);
	}
	
	@Test
	public void testAddTimechunk() {
		Time sTime = new Time(1, 0, 0);
//		Time dur = new Time(0, 30, 0);
		String durHr = "0";
		String durMin = "30";
//		session.close();
		
//		 //create a timechunk
		TimeChunk addedChunk = new TimeChunk("4", timeline, 1, sTime, null, durHr, durMin, "first chunk", null, null);
		timelineManager.addTimechunk(timeline, addedChunk);
//		TimeChunk addedChunk = timelineManager.timeChunkManager.addTimeChunk(new TimeChunk("4", 
//				timeline, 1, sTime, null, dur, "first chunk", null, null));
		TimeChunk foundChunk = timelineManager.timeChunkManager.getTimeChunkByIdAndTimeline(addedChunk);
		// ensure added
		assertEquals("did not find the added chunk: ", addedChunk, foundChunk);
	}
	
	
	@Test
	public void testGetTimeChunks() {
		
		Set<TimeChunk> datatest = timelineManager.getTimeChunks(timeline);
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
//		Time dur = new Time(0, 30, 0);
		String durHr = "0";
		String durMin = "30";
		
		//create a timechunk
		TimeChunk addedChunk = new TimeChunk("1", 
				timeline, 1, sTime, null, durHr, durMin, "group chunk 1", null, null);
		chunksToAdd.add(addedChunk);
		
		addedChunk = new TimeChunk("2", 
				timeline, 1, sTime, null, durHr, durMin, "group chunk 2", null, null);
		chunksToAdd.add(addedChunk);
		
		addedChunk = new TimeChunk("3", 
				timeline, 1, sTime, null, durHr, durMin, "group chunk 3", null, null);
		chunksToAdd.add(addedChunk);
		
		addedChunk = new TimeChunk("4", 
				timeline, 1, sTime, null, durHr, durMin, "group chunk 4", null, null);
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
	
	
	@Test
	public void testRemoveTimechunk() {
		// get and sort the timechunk data
		ArrayList<TimeChunk> data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		
		assertEquals("should have 3 chunks", 3, data.size());
		for (int i = 0; i < data.size(); i++) {
			assertEquals("invalid chunk id",data.get(i).getChunkId() , "" + (i + 1));
		}
		
		// delete a chunk by name
		timelineManager.deleteTimeChunk(data.get(1));
		
		data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		
		assertEquals("should have 2 chunks", 2, data.size());
		assertEquals("invalid chunk description", "chunk " + (1), data.get(0).getDescription());
		assertEquals("invalid chunk description", "chunk " + (3), data.get(1).getDescription());
	}
	
	
//	@Test
//	public void testGetTotalTime() {
//		// get the timeline
//		Timeline eventTimeline = timelineManager.getTimelineByEventId(timeline);
//		Time expectedTime = new Time(1, 35, 0);
//		
//		assertEquals("Did not get correct time", expectedTime, eventTimeline.getTotalTime());
//	}
//	
//	@Test
//	public void testSetStartTime() {
//		ArrayList<TimeChunk> data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
//		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
//		Time oldStartTime = new Time(12, 30, 0);
//		
//		// ensure correct startTime
//		assertEquals("Did not get correct startTime", oldStartTime, data.get(0).getStartTime());		
//		
//		Time newStartTime = new Time(1, 20, 0);
//		timelineManager.timeChunkManager.setTimeChunkStartTime(data.get(0), newStartTime);
//
//		Time expectedTime = new Time(1, 35, 0);
//		Timeline eventTimeline = timelineManager.getTimelineByEventId(timeline);
//		
//		//ensure correct updated startTime
//		data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
//		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
//		assertEquals("Did not get correct startTime", newStartTime, data.get(0).getStartTime());
//		
//		// ensure totaltime now reflects the updated timechunk
//		assertEquals("Did not get correct time", expectedTime, eventTimeline.getTotalTime());
//	}
//	
//	@Test
//	public void testSetDuration() {
//		ArrayList<TimeChunk> data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
//		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
//		Time oldDuration = new Time(0, 20, 0);
//		
//		// ensure correct duration
//		assertEquals("Did not get correct duration", oldDuration, data.get(0).getDuration());		
//		
//		Time newDuration = new Time(1, 20, 0);
//		timelineManager.timeChunkManager.setTimeChunkDuration(data.get(0), newDuration);
//
//		Time expectedTime = new Time(2, 35, 0);
//		Timeline eventTimeline = timelineManager.getTimelineByEventId(timeline);
//		
//		//ensure correct updated duration
//		data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
//		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
//		assertEquals("Did not get correct duration", newDuration, data.get(0).getDuration());
//		
//		// ensure totaltime now reflects the updated timechunk
//		assertEquals("Did not get correct time", expectedTime, eventTimeline.getTotalTime());
//	}
	
	@Test
	public void testSetDescription() {
		ArrayList<TimeChunk> data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		String oldDescription ="chunk 1";
		
		// ensure correct description
		assertEquals("Did not get correct description", oldDescription, data.get(0).getDescription());		
		
		String newDescription = "chunk 1 updated description";
		timelineManager.timeChunkManager.setTimeChunkDescription(data.get(0), newDescription);

		Timeline eventTimeline = timelineManager.getTimelineByEventId(timeline);
		
		//ensure correct updated description
		data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		assertEquals("Did not get correct description", newDescription, data.get(0).getDescription());
	}

	@Test
	public void testSetPosition() {
		ArrayList<TimeChunk> data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		int oldPosition = 1;
		
		// ensure correct position
		assertEquals("Did not get correct position", oldPosition, data.get(0).getPosition());		
		
		int newPosition = 4;
		timelineManager.timeChunkManager.setTimeChunkPosition(data.get(0), newPosition);

		Timeline eventTimeline = timelineManager.getTimelineByEventId(timeline);
		
		//ensure correct updated description
		data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		assertEquals("Did not get correct position", newPosition, data.get(0).getPosition());
	}
	
	@Test
	public void testSetClient() {
		Client newClient = new Client("1", "Kevin", "Bacon", "123 E Main St.", "1-614-322-4545", "kevin@Bacon.com", "n");
		newClient = clientManager.addClient(newClient);
		
		ArrayList<TimeChunk> data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		Client oldClient = null;
		
		// ensure correct Client
		assertEquals("Did not get correct Client", oldClient, data.get(0).getClient());		
		
		timelineManager.timeChunkManager.setTimeChunkClient(data.get(0), newClient);

		Timeline eventTimeline = timelineManager.getTimelineByEventId(timeline);
		
		//ensure correct updated client
		data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		Client foundClient = new Client(data.get(0).getClient());
		assertEquals("Did not get correct Client", newClient, foundClient);
	}
	
	
	@Test
	public void testSetPhotographer() {
		Photographer newPhotographer = new Photographer("1", "Kyle", "Bergun");
		
		newPhotographer = photogManager.addPhotographer(newPhotographer);
		ArrayList<TimeChunk> data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		Photographer oldPhotog = null;
		
		// ensure correct Photographer
		assertEquals("Did not get correct Photographer", 0, data.get(0).getPhotographers().size());		

		timelineManager.timeChunkManager.setPhotographer(data.get(0), newPhotographer);
		
		//ensure correct updated Photographer
		data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		Photographer foundPhotographer = (Photographer)data.get(0).getPhotographers().toArray()[0];
		assertEquals("Did not get correct Photographer", newPhotographer, foundPhotographer);
	}
	
	@Test
	public void testSetPhotographers() {
		Photographer newPhotographer = new Photographer("1", "Kyle", "Bergun");
		Photographer newPhotographer2 = new Photographer("2", "Steve", "Smith");
		
		newPhotographer = photogManager.addPhotographer(newPhotographer);
		newPhotographer2 = photogManager.addPhotographer(newPhotographer2);
		
		ArrayList<TimeChunk> data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		Photographer oldPhotog = null;
		
		Set<Photographer> photogs = new HashSet<Photographer>();
		photogs.add(newPhotographer);
		photogs.add(newPhotographer2);
		
		timelineManager.timeChunkManager.setPhotographers(data.get(0), photogs);
		timelineManager.timeChunkManager.setPhotographers(data.get(1), photogs);
		//ensure correct Photographers added
		data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		Photographer foundPhotographer = (Photographer)data.get(0).getPhotographers().toArray()[0];
		assertTrue("Did not get correct Photographer", data.get(0).getPhotographers().contains(newPhotographer));
		Photographer foundPhotographer2 = (Photographer)data.get(0).getPhotographers().toArray()[1];
		assertTrue("Did not get correct Photographer", data.get(0).getPhotographers().contains(newPhotographer2));
		
		// ensure setting back to one gives result of one photographer back
		timelineManager.timeChunkManager.setPhotographer(data.get(0), newPhotographer);
		
		//ensure correct set Photographer
		data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		foundPhotographer = (Photographer)data.get(0).getPhotographers().toArray()[0];
		assertEquals("Did not get correct Photographer", newPhotographer, foundPhotographer);
		assertEquals("wrong number of photographers", 1, data.get(0).getPhotographers().size());
	}
	
	
	@Test
	public void testaddPhotographer() {
		Photographer newPhotographer = new Photographer("1", "Kyle", "Bergun");
		Photographer newPhotographer2 = new Photographer("2", "Steve", "Smith");
		
		newPhotographer = photogManager.addPhotographer(newPhotographer);
		newPhotographer2 = photogManager.addPhotographer(newPhotographer2);
		
		ArrayList<TimeChunk> data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		Photographer oldPhotog = null;
		
		timelineManager.timeChunkManager.setPhotographer(data.get(0), newPhotographer);
		// add a new photographer
		timelineManager.timeChunkManager.addPhotographer(data.get(0), newPhotographer2);
		
		//ensure correct added Photographer
		data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		Photographer foundPhotographer = (Photographer)data.get(0).getPhotographers().toArray()[0];
		assertEquals("Did not get correct Photographer", newPhotographer, foundPhotographer);
		
		// check that new photographer was added
		foundPhotographer = (Photographer)data.get(0).getPhotographers().toArray()[1];
		assertTrue("Did not get correct Photographer", data.get(0).getPhotographers().contains(newPhotographer2));
		
		assertEquals("wrong number of photographers", 2, data.get(0).getPhotographers().size());
	}
	
	
	@Test
	public void testdeletePhotographer() {
		Photographer newPhotographer = new Photographer("1", "Kyle", "Bergun");
		Photographer newPhotographer2 = new Photographer("2", "Steve", "Smith");
		
		newPhotographer = photogManager.addPhotographer(newPhotographer);
		newPhotographer2 = photogManager.addPhotographer(newPhotographer2);
		
		ArrayList<TimeChunk> data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		Photographer oldPhotog = null;
		
		Set<Photographer> photogs = new HashSet<Photographer>();
		photogs.add(newPhotographer);
		photogs.add(newPhotographer2);
		
		timelineManager.timeChunkManager.setPhotographers(data.get(0), photogs);
		
		// ensure correct size
		data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		assertEquals("wrong number of photographers", 2, data.get(0).getPhotographers().size());
		
		// delete first photographer
		timeChunk = data.get(0);
		
		timelineManager.timeChunkManager.deletePhotographer(timeChunk, newPhotographer);
		
		//ensure correct Photographer left
		data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		
		Photographer foundPhotographer = (Photographer)data.get(0).getPhotographers().toArray()[0];
		assertTrue("Did not get correct Photographer", data.get(0).getPhotographers().contains(newPhotographer2));
		assertEquals("wrong number of photographers", 1, data.get(0).getPhotographers().size());
	}
	
	@Test
	public void testdeletePhotographers() {
		Photographer newPhotographer = new Photographer("1", "Kyle", "Bergun");
		Photographer newPhotographer2 = new Photographer("2", "Steve", "Smith");
		Photographer newPhotographer3 = new Photographer("3", "Joe", "Smith");
		
		newPhotographer = photogManager.addPhotographer(newPhotographer);
		newPhotographer2 = photogManager.addPhotographer(newPhotographer2);
		newPhotographer3 = photogManager.addPhotographer(newPhotographer3);
		
		ArrayList<TimeChunk> data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		Photographer oldPhotog = null;
		
		Set<Photographer> photogs = new HashSet<Photographer>();
		photogs.add(newPhotographer);
		photogs.add(newPhotographer2);
		photogs.add(newPhotographer3);
		
		photogs = timelineManager.timeChunkManager.setPhotographers(data.get(0), photogs).getPhotographers();
		
		// ensure correct size
		data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		assertEquals("wrong number of photographers", 3, data.get(0).getPhotographers().size());
		
		// delete first and last photographers
		timeChunk = data.get(0);
		photogs.remove(newPhotographer2);
		timelineManager.timeChunkManager.deletePhotographers(timeChunk, photogs);
		
		//ensure correct Photographer left
		data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		Photographer foundPhotographer = (Photographer)data.get(0).getPhotographers().toArray()[0];
		assertEquals("Did not get correct Photographer", newPhotographer2, foundPhotographer);
		assertEquals("wrong number of photographers", 1, data.get(0).getPhotographers().size());
	}
	
	@Test
	public void testSetLocation() {
		Location home = new Location(null, "Pickerington", "OH", "43147", "302 west applegate St.", "Home");

		ArrayList<TimeChunk> data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		Location newLocation = null;
		
		newLocation = data.get(0).getLocation();
		
		assertNull("should be null", newLocation);
		
		newLocation = timelineManager.timeChunkManager.setTimeChunkLocation(data.get(0), home).getLocation();
		
		//ensure location set
		data = new ArrayList<TimeChunk>(timelineManager.getTimeChunks(timeline));
		Collections.sort(data, timelineManager.timeChunkManager.chunkIdComparator);
		Location foundLocation = data.get(0).getLocation();
		assertEquals("Did not get correct Location", home, newLocation);
	}
	
	private void cleanUp(List<Timeline> timelineList) {
		// TODO Auto-generated method stub
		Iterator<Timeline> timelineIterator = timelineList.iterator();
		Timeline temp;
		while (timelineIterator.hasNext()) {
			temp = timelineIterator.next();
			try {
				timelineManager.DeleteTimelineByEventId(temp);
			}
			catch (Exception e) {
				// do nothing
			}
		}
	}
	
	private void createEventType() {
		
		 eventType = new com.defrainphoto.weddingtracker.model.EventType
				("1", "basic Event", "$50");
		
		try {
			
			openSession();

			session.beginTransaction();
			session.saveOrUpdate(eventType);
			Hibernate.initialize(eventType);
			session.getTransaction().commit();
			
		}
		
		catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("No eventType created");
		}
		
		finally {
			closeSession();
		}
	}
	
	private void createEvent() {
		Date date = Date.valueOf("2017-10-5");
//		Integer startTimeHr = 12;
//		Integer startTimeMn = 30;
		Time duration = new Time(0, 0, 0);
		String extraCost = "";
		
		event = new Event("1", "the big Event", eventType, date, startTime, duration, null, extraCost, "n", null, null);
		
		try {
			openSession();

			session.beginTransaction();
//			session.createSQLQuery("insert into Event(eventid, eventname, type, eventdate, starttime, notes)" +
//			" values(1, the big event, " + date.toString() + ", 12:30:00, the photog will be late)");
			session.saveOrUpdate(event);
			Hibernate.initialize(event);
			session.getTransaction().commit();
		}
		
		catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("No event created");
		}
		
		finally {
			closeSession();
		}
		
	}
	
	private void createTimeline() {		
		//create a timeline
		startTime = Time.valueOf("11:0:0");
		duration = Time.valueOf("0:0:0");
		timeline = new Timeline(event.getEventId(), event, null, startTime, duration);
		try {
			openSession();
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
			
			session.beginTransaction();
			session.createSQLQuery("insert into time_chunk(chunkid, eventid, position, starttime, duration, description)" + 
					" values(1, 1, 1, '12:30:00', '00:20:00', 'chunk 1')").executeUpdate();
			session.createSQLQuery("insert into time_chunk(chunkid, eventid, position, starttime, duration, description)" + 
					" values(2, 1, 2, '13:00:00', '00:30:00', 'chunk 2')").executeUpdate();
			session.createSQLQuery("insert into time_chunk(chunkid, eventid, position, starttime, duration, description)" + 
					" values(3, 1, 3, '13:30:00', '00:45:00', 'chunk 3')").executeUpdate();
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
		session.getTransaction().commit();
		
		closeSession();
	}

	private void deleteEventType() {
		openSession();
		
		session.beginTransaction();
		session.createQuery("delete from " + EventType.class.getName()).executeUpdate();
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
	
	private void deleteClients() {
		openSession();
		
		session.beginTransaction();
		session.createQuery("delete from " + Client.class.getName()).executeUpdate();
		session.getTransaction().commit();

		closeSession();		
	}
	
	private void deletePhotographers() {
		openSession();
		
		session.beginTransaction();
		session.createQuery("delete from " + Photographer.class.getName()).executeUpdate();
		session.getTransaction().commit();

		closeSession();	
	}
	
	private void openSession() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	private void closeSession() {
		if (session.isOpen()) {
			session.close();
		}
	}

}
