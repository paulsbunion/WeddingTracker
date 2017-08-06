package main.java.com.defrainphoto.weddingtracker.model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityExistsException;

import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import main.java.com.defrainphoto.weddingtracker.model.HibernateUtil;;

public class TimeChunkManager {
	Session session;

	public TimeChunk addTimeChunk(TimeChunk newChunk) {
		boolean found = false;
		TimeChunk temp = null;
		
		// if already in DB, throw exception
		temp = findTimeChunk(newChunk, false, true, true);
		
		if (temp != null) {
			throw new EntityExistsException("Entity already Exists:  " + newChunk .toString());
			}
		
		
		// else, open session, save, and commit
		else {
			openSession();
			
			int newId = getNextId(newChunk);
			newChunk.setChunkId(newId);
			
			session.beginTransaction();
			session.save(newChunk);
			session.getTransaction().commit();
			
			temp = findTimeChunk(newChunk, true, true, false);
			newChunk = temp; 
			closeSession();
			found = true;
		}
		
		return found ? newChunk : null;
		
	}
	
	private int getNextId(TimeChunk newChunk) {
		int nextId = -1;
		List<TimeChunk> foundChunks = new ArrayList<TimeChunk>(); 
		
		openSession();
		
		try {
			session.beginTransaction();
			
			StringBuilder queryString = new StringBuilder("from TIME_CHUNK where ");
			buildQuery(queryString, false, true, false);
			
			Query query = session.createQuery(queryString.toString());
			System.out.println(query.toString());
			setQueryVariables(newChunk, query, false, true, false);

			List list = query.list();
			
			session.getTransaction().commit();
			
			if (list != null && !list.isEmpty()) {
				foundChunks = (List<TimeChunk>) list;
				Collections.sort(foundChunks, chunkIdComparator);
				nextId = foundChunks.get(foundChunks.size() - 1).getChunkId();
				nextId++;
			}
		}
		
		catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		closeSession();
		
		return nextId;
	}

	public TimeChunk getTimeChunkByDescriptionAndTimeline(TimeChunk timeChunk) {
		TimeChunk result;
		
		result = findTimeChunk(timeChunk, false, true, true);
		
		return result; 
	}
	
	public TimeChunk getTimeChunkByIdAndTimeline(TimeChunk timeChunk) {
		TimeChunk result;
		
		result = findTimeChunk(timeChunk, true, true, false);
		
		return result; 
	}
	
	public TimeChunk setTimeChunkPosition(TimeChunk timeChunk, int newPosition) {
		return setTimeChunkHelper(timeChunk, newPosition, null, null, null, null, null, null, 
				true, false, false, false, false, false, false);
	}
	
	public TimeChunk setTimeChunkStartTime(TimeChunk timeChunk, Time newStartTime) {
		return setTimeChunkHelper(timeChunk, null, newStartTime, null, null, null, null, null, 
				false, true, false, false, false, false, false);
	}
	
	public TimeChunk setTimeChunkLocation(TimeChunk timeChunk, Location newLocation) {
		return setTimeChunkHelper(timeChunk, null, null, newLocation, null, null, null, null, 
				false, false, true, false, false, false, false);
	}
	
	public TimeChunk setTimeChunkDuration(TimeChunk timeChunk, Time newDuration) {
		return setTimeChunkHelper(timeChunk, null, null, null, newDuration, null, null, null, 
				false, false, false, true, false, false, false);
	}
	
	public TimeChunk setTimeChunkDescription(TimeChunk timeChunk, String newDescription) {
		return setTimeChunkHelper(timeChunk, null, null, null, null, newDescription, null, null, 
				false, false, false, false, true, false, false);
	}
	
	public TimeChunk setTimeChunkClient(TimeChunk timeChunk, Client newClient) {
		return setTimeChunkHelper(timeChunk, null, null, null, null, null, newClient, null, 
				false, false, false, false, false, true, false);
	}
	
	public TimeChunk setPhotographer(TimeChunk timeChunk, Set<Photographer> newPhotographers) {
		return setTimeChunkHelper(timeChunk, null, null, null, null, null, null, newPhotographers, 
				false, false, false, false, false, false, true);
	}
	
	private TimeChunk findTimeChunk(TimeChunk timeChunk, boolean byChunkId, boolean byEventId, boolean byDescription) {
		boolean found = false;
		openSession();
		
		try {
			session.beginTransaction();
			
			StringBuilder queryString = new StringBuilder("from TIME_CHUNK where ");
			buildQuery(queryString, byChunkId, byEventId, byDescription);
			
			Query query = session.createQuery(queryString.toString());
			System.out.println(query.toString());
			setQueryVariables(timeChunk, query, byChunkId, byEventId, byDescription);

			List list = query.list();
			
			session.getTransaction().commit();
			
			if (list != null && !list.isEmpty()) {
				timeChunk = (TimeChunk) list.get(0);
				found = true;
			}
			
			else {
				found = false;
			}
		}
		
		catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		closeSession();
		
		return found ? timeChunk : null;
	}
	
	private void buildQuery(StringBuilder queryString, boolean byChunkId, boolean byEventId, boolean byDescription) {
		boolean moreThanOne = false;
		
		if (byChunkId) {
			queryString.append("chunkid = :chunkid ");
			moreThanOne = true;
		}
		if (byEventId) {
			if (moreThanOne) {
				queryString.append("AND ");
			}
			queryString.append("eventid = :eventid ");
			moreThanOne = true;			
		}
		
		if (byDescription) {
			if (moreThanOne) {
				queryString.append("AND ");
			}
			queryString.append("description = :description ");
			moreThanOne = true;
		}		
	}
	
	private void setQueryVariables(TimeChunk timeChunk, Query query, boolean byChunkId, boolean byEventId, boolean byDescription) {
		if (byChunkId) {
			query.setParameter("chunkid", timeChunk.getChunkId());
		}
		if (byEventId) {
			query.setParameter("eventid", timeChunk.getTimeline().getEvent().getEventId());
		}
		if (byDescription) {
			query.setParameter("description", timeChunk.getDescription());
		}
		
	}
	
	private TimeChunk setTimeChunkHelper(TimeChunk timeChunk, Integer newPosition, Time newStartTime, Location newLocation, Time newDuration,
			String newDescription, Client newClient, Set<Photographer> newPhotographers, boolean updatePosition, boolean updateStartTime, boolean updateLocation, boolean updateDuration,
			boolean updateDescription, boolean updateClient, boolean updatePhotographers) {
		
		// if any new value to update is null and should not be, throw exception
//		if (updatePosition && newPosition == null) {
//		throw new IllegalArgumentException("cannot update field to Null: newPosition");
//		}
		if (updateStartTime && newStartTime == null) {
			 throw new IllegalArgumentException("cannot update field to Null: newStartTime");
		}
//		if (updateLocation && newLocation == null) {
//			throw new IllegalArgumentException("cannot update field to Null: lastName");
//		}
//		if (updateDuration && newDuration == null) {
//			throw new IllegalArgumentException("cannot update field to Null: Address");
//		}
		if (updateDescription && newDescription == null) {
			throw new IllegalArgumentException("cannot update field to Null: newDescription");
		}
//		if (updateClient && newClient == null) {
//			throw new IllegalArgumentException("cannot update field to Null: email");
//		}
		
//		if (updatePhotographer && newPhotographer == null) {
//			throw new IllegalArgumentException("cannot update field to Null: email");
//		}
				
		// store update status
		boolean valid = false;
		
		// Strings to store old data
		int oldPosition = timeChunk.getPosition();
		Time oldStartTime = timeChunk.getStartTime();
		Location oldLocation = timeChunk.getLocation();
		Time oldDuration = timeChunk.getDuration();
		String oldDescription = timeChunk.getDescription();
		Client oldClient = timeChunk.getClient();
		Set<Photographer> oldPhotographers = timeChunk.getPhotographers(); 
		
		// set new parameters
		if (!updatePosition) {
			newPosition = oldPosition;
		}
		if (!updateStartTime) {
			newStartTime = oldStartTime;
		}
		if (!updateLocation) {
			newLocation = oldLocation;
		}
		if (!updateDuration) {
			newDuration = oldDuration;
		}
		if (!updateDescription) {
			newDescription = oldDescription;
		}
		if (!updateClient) {
			newClient = oldClient;
		}
		
		if (!updatePhotographers) {
			newPhotographers = oldPhotographers;
		}
		
		// first try to find the timeChunk
		TimeChunk foundTimeChunk = findTimeChunk(timeChunk, false, true, true);
		
		TimeChunk foundTimeChunkConflict = null;
		
		// if updating the startTime, check if the new startTime already exists
		if (updateStartTime) {
			foundTimeChunkConflict = new TimeChunk(timeChunk.getChunkId(), timeChunk.getTimeline(), timeChunk.getPosition(),
					newStartTime, timeChunk.getLocation(), timeChunk.getDuration(), timeChunk.getDescription(), 
					timeChunk.getClient(), timeChunk.getPhotographers());
			
			foundTimeChunkConflict = findTimeChunk(foundTimeChunkConflict, false, true, true);
			
			if (foundTimeChunkConflict != null) {
				throw new EntityExistsException("Cannot have tow chunks at the same time, add code to offe user to shift all timechunks: " + foundTimeChunkConflict.toString());
				/*
				 * set offset time to the difference in overlap
				 * 
				 * get all chunks by timeline id for the timeline
				 * 
				 * starting at the latest chunk, add the offest to the chunk's starttime. 
				 */
			}
		}
		
		// If the Timechunk exists, Update the timeChunk and set old parameters to new parameters
		try {
			if (foundTimeChunk != null && foundTimeChunkConflict == null) {
				
				foundTimeChunk.setPosition(newPosition);
				foundTimeChunk.setStartTime(newStartTime);
				foundTimeChunk.setLocation(newLocation);
				foundTimeChunk.setDuration(newDuration);
				foundTimeChunk.setDescription(newDescription);
				foundTimeChunk.setClient(newClient);
				foundTimeChunk.setPhotographers(newPhotographers);
				
				// update the client id and new last name
				timeChunk.setPosition(newPosition);
				timeChunk.setStartTime(newStartTime);
				timeChunk.setLocation(newLocation);
				timeChunk.setDuration(newDuration);
				timeChunk.setDescription(newDescription);
				timeChunk.setClient(newClient);
				timeChunk.setPhotographers(newPhotographers);
				
				// open new session and commit
				openSession();
					
				session.beginTransaction();
				session.saveOrUpdate(foundTimeChunk);
				session.getTransaction().commit();
				
				closeSession();
				
				valid = true;
			}
		}
		catch(HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		finally {
			if (session.isOpen()) {
				closeSession();
			}
			
			// if not valid, reset to old name
			if (!valid) {
				timeChunk.setPosition(oldPosition);
				timeChunk.setStartTime(oldStartTime);
				timeChunk.setLocation(oldLocation);
				timeChunk.setDuration(oldDuration);
				timeChunk.setDescription(oldDescription);
				timeChunk.setClient(oldClient);
				timeChunk.setPhotographers(oldPhotographers);
			}
		}
		return valid ? timeChunk : null;
	}
	
	private void openSession() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	private void closeSession() {
		if (session.isOpen()) {
			try {
				session.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	Comparator<TimeChunk> chunkIdComparator = new Comparator<TimeChunk>() {
		
		public int compare(TimeChunk o1, TimeChunk o2) {
			return Integer.valueOf(o1.getChunkId()).compareTo(Integer.valueOf(o2.getChunkId()));
		}
	};
}
