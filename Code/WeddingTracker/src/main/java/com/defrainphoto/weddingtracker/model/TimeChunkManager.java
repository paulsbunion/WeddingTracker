package main.java.com.defrainphoto.weddingtracker.model;

import java.sql.Time;
import java.util.List;

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
		temp = findTimeChunk(newChunk);
		
		if (temp != null) {
			throw new EntityExistsException("Entity already Exists:  " + newChunk .toString());
			}
		
		
		// else, open session, save, and commit
		else {
			openSession();
			
			session.beginTransaction();
			session.save(newChunk);
			session.getTransaction().commit();
			
			temp = findTimeChunk(newChunk);
			newChunk = temp; 
			closeSession();
			found = true;
		}
		
		return found ? newChunk : null;
		
	}
	
	public TimeChunk getTimeChunk(TimeChunk timeChunk) {
		TimeChunk result;
		
		result = findTimeChunk(timeChunk);
		
		return result; 
	}
	
	public TimeChunk setTimeChunkStartTime(TimeChunk timeChunk, Time newStartTime) {
		return setTimeChunkHelper(timeChunk, newStartTime, null, null, null, null, null, true, false, false, false, false, false);
	}
	
	public TimeChunk setTimeChunkLocation(TimeChunk timeChunk, Location newLocation) {
		return setTimeChunkHelper(timeChunk, null, newLocation, null, null, null, null, false, true, false, false, false, false);
	}
	
	public TimeChunk setTimeChunkDuration(TimeChunk timeChunk, Time newDuration) {
		return setTimeChunkHelper(timeChunk, null, null, newDuration, null, null, null, false, false, true, false, false, false);
	}
	
	public TimeChunk setTimeChunkDescription(TimeChunk timeChunk, String newDescription) {
		return setTimeChunkHelper(timeChunk, null, null, null, newDescription, null, null, false, false, false, true, false, false);
	}
	
	public TimeChunk setTimeChunkClient(TimeChunk timeChunk, Client newClient) {
		return setTimeChunkHelper(timeChunk, null, null, null, null, newClient, null, false, false, false, false, true, false);
	}
	
	public TimeChunk setPhotographer(TimeChunk timeChunk, Photographer newPhotographer) {
		return setTimeChunkHelper(timeChunk, null, null, null, null, null, newPhotographer, false, false, false, false, false, true);
	}
	
	private TimeChunk findTimeChunk(TimeChunk newChunk) {
		boolean found = false;
		openSession();
		
		try {
			session.beginTransaction();
			
			StringBuilder queryString = new StringBuilder("from TIME_CHUNK where TIMELINEID = :timelineId AND STARTTIME = :starttime");
			Query query = session.createQuery(queryString.toString());
			query.setParameter("timelineId", newChunk.getTimelineId());
			query.setParameter("starttime", newChunk.getStartTime());
			
			List list = query.list();
			
			session.getTransaction().commit();
			
			if (list != null && !list.isEmpty()) {
				newChunk = (TimeChunk) list.get(0);
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
		
		return found ? newChunk : null;
	}
	
	private TimeChunk setTimeChunkHelper(TimeChunk timeChunk, Time newStartTime, Location newLocation, Time newDuration,
			String newDescription, Client newClient, Photographer newPhotographer, boolean updateStartTime, boolean updateLocation, boolean updateDuration,
			boolean updateDescription, boolean updateClient, boolean updatePhotographer) {
		
		// if any new value to update is null and should not be, throw exception
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
		Time oldStartTime = timeChunk.getStartTime();
		Location oldLocation = timeChunk.getLocation();
		Time oldDuration = timeChunk.getDuration();
		String oldDescription = timeChunk.getDescription();
		Client oldClient = timeChunk.getClient();
		Photographer oldPhotographer = timeChunk.getPhotographer(); 
		
		// set new parameters
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
		
		if (!updatePhotographer) {
			newPhotographer = oldPhotographer;
		}
		
		// first try to find the timeChunk
		TimeChunk foundTimeChunk = findTimeChunk(timeChunk);
		
		TimeChunk foundTimeChunkConflict = null;
		
		// if updating the startTime, check if the new startTime already exists
		if (updateStartTime) {
			foundTimeChunkConflict = new TimeChunk(timeChunk.getTimelineId(), newStartTime, timeChunk.getLocation(), 
					timeChunk.getDuration(), timeChunk.getDescription(), timeChunk.getClient(), timeChunk.getPhotographer());
			
			foundTimeChunkConflict = findTimeChunk(foundTimeChunkConflict);
			
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
				
				foundTimeChunk.setStartTime(newStartTime);
				foundTimeChunk.setLocation(newLocation);
				foundTimeChunk.setDuration(newDuration);
				foundTimeChunk.setDescription(newDescription);
				foundTimeChunk.setClient(newClient);
				foundTimeChunk.setPhotographer(newPhotographer);
				
				// update the client id and new last name
				timeChunk.setStartTime(newStartTime);
				timeChunk.setLocation(newLocation);
				timeChunk.setDuration(newDuration);
				timeChunk.setDescription(newDescription);
				timeChunk.setClient(newClient);
				timeChunk.setPhotographer(newPhotographer);
				
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
				timeChunk.setStartTime(oldStartTime);
				timeChunk.setLocation(oldLocation);
				timeChunk.setDuration(oldDuration);
				timeChunk.setDescription(oldDescription);
				timeChunk.setClient(oldClient);
				timeChunk.setPhotographer(oldPhotographer);
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
}
