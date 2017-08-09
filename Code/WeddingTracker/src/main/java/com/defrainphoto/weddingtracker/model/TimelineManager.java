package main.java.com.defrainphoto.weddingtracker.model;

import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityExistsException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class TimelineManager {
	private Session session;
	public TimeChunkManager timeChunkManager = new TimeChunkManager();
	
	public Timeline addTimeline(Timeline newTimeline) {
		boolean found = false;
		System.out.println("the ole goodie");
		System.out.println(newTimeline.toString());
		Timeline temp = findTimeline(newTimeline, true);
		
		// if already in DB, throw exception
		if (temp != null) {
			throw new EntityExistsException("Entity already Exists:  " + newTimeline.toString());
		}
		
		// else, open session, save, and commit
		else {
			openSession();
			System.out.println("now adding the dang thing");
			session.beginTransaction();
			System.out.println(newTimeline.toString());
			session.save(newTimeline);
			session.getTransaction().commit();
			Hibernate.initialize(newTimeline);
			closeSession();
			
			System.out.println("going to find");
			temp = findTimeline(newTimeline, true);
			newTimeline.setEvent(temp.getEvent()); 
			
			found = true;
		}
		
		return found ? newTimeline : null;
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
//		System.out.println("not null here: " + newChunk.toString());
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
		System.out.println("in the delete timeline method");
		System.out.println(timeline.toString());
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
		System.out.println("IN HERE");
		openSession();
		
		session.beginTransaction();
		System.out.println("preQuery");
		StringBuilder queryString = new StringBuilder("from Timeline where ");
		buildQuery(queryString, byEventId);
		System.out.println("QueryBuilt");
		Query query = session.createQuery(queryString.toString());
		System.out.println("queryCreated");
		System.out.println(query.toString());
		System.out.println("setting variables");
		setQueryVariables(timeline, query, byEventId);
		System.out.println("variables set");
		System.out.println(byEventId);
		System.out.println("should be true");
		System.out.println(timeline.getEvent().getEventId());
		System.out.println("IN HERE");
		System.out.println(queryString.toString());
		
		List list = query.list();
		
		session.getTransaction().commit();
		Hibernate.initialize(timeline.getTimeChunks());
		
		System.out.println("found one? ");
		
		if (list != null && !list.isEmpty()) {
			System.out.println("converting");
			Timeline temp = (Timeline) list.get(0);
			System.out.println("found one: " + temp.toString());
			timeline = temp;
			found = true;
		}
		
		else {
			System.out.println("not found");
			found = false;
		}
		
		Hibernate.initialize(timeline);
		closeSession();
		
		return found ? timeline : null;
	}
	
	private void setQueryVariables(Timeline timeline, Query query, boolean byEventId) {
		System.out.println("YO:" + byEventId);
		if (byEventId) {
			System.out.println(timeline.toString());
			System.out.println("in set variables: " + timeline.getEventId());
			query.setParameter("eventid", timeline.getEventId());
			System.out.println("done in set variables");
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
