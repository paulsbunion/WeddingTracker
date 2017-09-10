package com.defrainphoto.weddingtracker.model;

import java.sql.Time;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityExistsException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimelineManager {
	private Session session;
	@Autowired
	public TimeChunkManager timeChunkManager;
//	public TimeChunkManager timeChunkManager = new TimeChunkManager(this);	
	@Autowired
	public EventManager eventManager;
//	public EventManager eventManager = new EventManager();
	
	public void setTimeChunkManager(TimeChunkManager timechunkManager) {
		this.timeChunkManager = timechunkManager;
	}
	
	public void setEventManager(EventManager eventManager) {
		this.eventManager = eventManager;
	}
	
	public Timeline addTimeline(Timeline newTimeline) {
		boolean found = false;
		Timeline temp = findTimeline(newTimeline, true);
		
		// if already in DB, throw exception
		if (temp != null) {
			throw new EntityExistsException("Entity already Exists:  " + newTimeline.toString());
		}
		// else, open session, save, and commit
		else {
			// make sure not null event in timeline
			updateEventInTimeline(newTimeline);
			
			openSession();
			session.beginTransaction();
			session.save(newTimeline);
			session.getTransaction().commit();
			Hibernate.initialize(newTimeline);
			closeSession();
			
			temp = findTimeline(newTimeline, true);
			newTimeline.setEvent(temp.getEvent()); 
			found = true;
		}
		
		return found ? newTimeline : null;
	}
	
	public Map<String, String> getallTimelineIds() {

		boolean found = false;
		List<Timeline> result = null;
		Map<String, String> timelineIds = new HashMap<String, String>();
		openSession();
		
		session.beginTransaction();
		StringBuilder queryString = new StringBuilder("from Timeline");
		Query query = session.createQuery(queryString.toString());
		
		List list = query.list();
		
		session.getTransaction().commit();
		
		if (list != null && !list.isEmpty()) {
			result  = (List<Timeline>) list;
			found = true;
		}
		
		else {
			found = false;
		}
		Hibernate.initialize(list);
		closeSession();
		
		// put the list in the map
		if  (result != null) {
			for (Timeline t : result) {
				timelineIds.put(t.getEventId(), t.getEventId());
			}
		}
		return found ? timelineIds : null;
	}
	
	private void updateEventInTimeline(Timeline newTimeline) {
		Event event = new Event();
		event.setEventId(newTimeline.getEventId());
		event = eventManager.getEventById(event);
		newTimeline.setEvent(event);
	}

	public Timeline getTimelineByEventId(Timeline timeline) {
		Timeline result;
		
		result = findTimeline(timeline, true);
		return result;
	}
	
	public Set<TimeChunk> getTimeChunks(Timeline timeline) {
		boolean found = false;
		Set<TimeChunk> foundChunks = null;
		Timeline result;
		
		result = findTimeline(timeline, true);
		if (result != null) {
			found = true;
			foundChunks = new HashSet<TimeChunk>(timeChunkManager.getTimeChunksByTimeline(timeline)); 
		}
		return found ? foundChunks : null;
	}
	
	public Timeline setTimeChunks(Timeline timeline, Set<TimeChunk> timeChunks) {
		Set<TimeChunk> oldChunks = timeline.getTimeChunks();
		
		timeline.setTimeChunks(timeChunks);
		
		openSession();
		try {
			session.beginTransaction();
			// delete old
			session.delete(timeline);
			// save new timeline
			session.saveOrUpdate(timeline);
			session.getTransaction().commit();
		}
		
		catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			timeline.setTimeChunks(oldChunks);
			throw(e);
		}
		
		finally {
			Hibernate.initialize(timeline);
			closeSession();
		}
		
		return timeline;
	}
	
	public Timeline addTimechunk (Timeline timeline, TimeChunk newChunk) {
		newChunk.setTimeline(timeline);
		timeChunkManager.addTimeChunk(newChunk);
//		Set<TimeChunk> oldChunks = timeline.getTimeChunks();
//		Set<TimeChunk> timeChunks = timeline.getTimeChunks();
//		if (timeChunks == null) {
//			timeChunks = new HashSet<TimeChunk>();
//		}
//		
//		timeChunks.add(newChunk);
//		openSession();
//		try {
//			session.beginTransaction();
//			session.saveOrUpdate(timeline);
//			session.getTransaction().commit();
//		}
//		
//		catch (HibernateException e) {
//			e.printStackTrace();
//			timeline.setTimeChunks(oldChunks);
//			throw(e);
//		}
//		
//		finally {
//			session.getTransaction().rollback();
//			closeSession();
//		}
//		
		return timeline;
	}
	
	public Timeline removeTimechunk (Timeline timeline, TimeChunk removeChunk) {
		Set<TimeChunk> oldChunks = timeline.getTimeChunks();
		
		Set<TimeChunk> foundChunks = timeline.getTimeChunks();
		
		TimeChunk foundChunk = null;
		for (TimeChunk t : foundChunks) {
			if (t.getChunkId() == removeChunk.getChunkId()) {
				foundChunk = t;
				break;
			}
		}
		
		if (foundChunk != null) {
			foundChunks.remove(foundChunk);
		}
		
		openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(timeline);
			session.getTransaction().commit();
		}
		
		catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			timeline.setTimeChunks(oldChunks);
			throw(e);
		}
		
		finally {
			Hibernate.initialize(timeline);
			closeSession();
		}
		
		return timeline;
	}
	
	public Time getTotalTime(Timeline timeline) {
		Time totalTime = null;
		Timeline foundTimeline = getTimelineByEventId(timeline);
		
		if (foundTimeline != null) {
			totalTime = timeline.getTotalTime();
		}
		return totalTime;
	}
	
		
	public Timeline DeleteTimelineByEventId(Timeline timeline) {
		boolean deleted = false;
		Timeline foundTimeline = null;
		// check if in database
		foundTimeline = findTimeline(timeline, true);
		
		if (foundTimeline != null) {
			deleted = deleteTimeline(foundTimeline);
		}

		return deleted? foundTimeline : null;
	}
	
	// helper method to delete from database. returns success / failure status
		private boolean deleteTimeline(Timeline foundTimeline) {
			boolean success = false;
			try {
				openSession();
				
				session.beginTransaction();
				session.delete(foundTimeline);
				session.getTransaction().commit();
				closeSession();
				success = true;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			return success;
		}
	
	private Timeline findTimeline(Timeline timeline, boolean byEventId) {
		boolean found = false;
		openSession();
		
		session.beginTransaction();
		StringBuilder queryString = new StringBuilder("from Timeline where ");
		buildQuery(queryString, byEventId);
		Query query = session.createQuery(queryString.toString());
		setQueryVariables(timeline, query, byEventId);
		
		List list = query.list();
		
		session.getTransaction().commit();
		Hibernate.initialize(timeline.getTimeChunks());
		
		if (list != null && !list.isEmpty()) {
			Timeline temp = (Timeline) list.get(0);
			timeline = temp;
			found = true;
		}
		
		else {
			found = false;
		}
		
		Hibernate.initialize(timeline);
		Hibernate.initialize(timeline.getTimeChunks());
		closeSession();
		
		return found ? timeline : null;
	}
	
	private void setQueryVariables(Timeline timeline, Query query, boolean byEventId) {
		if (byEventId) {
			query.setParameter("eventid", timeline.getEventId());
		}
		
	}

	private void buildQuery(StringBuilder queryString, boolean byEventId) {
		boolean moreThanOne = false;
		
		if (byEventId) {
			queryString.append("eventId = :eventid ");
			moreThanOne = true;
		}
	}
	
	private void openSession() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	private void closeSession() {
		session.close();
	}

	public boolean deleteTimeChunk(TimeChunk timeChunk) {
		boolean success = false;
		try {
			openSession();
			
			session.beginTransaction();
			session.delete(timeChunk);
			session.getTransaction().commit();
			closeSession();
			success = true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return success;
		
	}
}
