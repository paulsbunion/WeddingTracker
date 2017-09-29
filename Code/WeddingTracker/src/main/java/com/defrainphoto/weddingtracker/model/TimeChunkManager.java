package com.defrainphoto.weddingtracker.model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.defrainphoto.weddingtracker.model.HibernateUtil;;

@Component
public class TimeChunkManager {
	@Autowired
	private LocationManager locationManager;
//	private LocationManager locationManager = new LocationManager();
	@Autowired
	private TimelineManager timelineManager;
	@Autowired 
	private MileageManager mileageManager;
	
	Session session;

//	public TimeChunkManager(TimelineManager timelineManager) {
//		this.timelineManager = timelineManager;
//	}
	
	public void setLocationManager(LocationManager locationManager) {
		this.locationManager = locationManager;
	}
	
	public void setTimelineManager(TimelineManager timelineManager) {
		this.timelineManager = timelineManager;
	}
	
	public TimeChunkManager() {
		// TODO Auto-generated constructor stub
	}

	public TimeChunk addTimeChunk(TimeChunk newChunk) {
		boolean found = false;
		TimeChunk temp = null;
		
		// make sure not null timeline in timechunk
		updateTimelineInTimeChunk(newChunk);
					
		// if already in DB, throw exception
		temp = findTimeChunk(newChunk, false, true, true);
		
//		if (temp != null) {
//			throw new EntityExistsException("Entity already Exists:  " + newChunk .toString());
//			}
//		
//		
//		// else, open session, save, and commit
//		else {
			String newId = getNextId(newChunk);
			newChunk.setChunkId(newId);
			
			openSession();
			session.beginTransaction();
			session.save(newChunk);
			session.getTransaction().commit();
			temp = findTimeChunk(newChunk, true, true, false);
			temp.setEventId(newChunk.getEventId());
			newChunk = temp; 
			
			Hibernate.initialize(temp);
			Hibernate.initialize(temp.getEventId());
			System.out.println("the eventid here: " + temp.getEventId());
			closeSession();
			found = true;
//		}
			
			// if the location should be tracked, update the event mileage
//			if (found && newChunk.getTrackMileage().equals("Y")) {
			if (found) {
				mileageManager.updateEventMileage(newChunk);
			}
		
		return found ? newChunk : null;
		
	}
	
	public TimeChunk updateTimeChunk(TimeChunk timeChunk) {
		
		boolean found = false;
		String wasTracked;
		TimeChunk temp = findTimeChunk(timeChunk, true, true, false);
		
		// if not in DB, throw exception
		if (temp == null) {
			throw new EntityNotFoundException("Entity not Found:  " + timeChunk.toString());
		}
		
		// else, open session, save, and commit
		else {
			wasTracked = temp.getTrackMileage();
			
			openSession();
			
			session.beginTransaction();
			System.out.println("in the update");
			session.saveOrUpdate(timeChunk);
			session.getTransaction().commit();
			
			Hibernate.initialize(timeChunk);
			Hibernate.initialize(timeChunk.getTimeline());
			closeSession();
			found = true;
		}
		
		// if the location should be tracked, update the event mileage
		// this occurs when it was before, but no longer is, or when it is now being tracked
//		if (found && (timeChunk.getTrackMileage().equals("Y") || wasTracked.equals("Y")) ) {
		if (found) {
		
			mileageManager.updateEventMileage(timeChunk);
		}
		
		return found ? timeChunk : null;
	}
	
	public List<TimeChunk> getAllChunksByEventId (String eventId) {
		boolean found = false;
		List<TimeChunk> result = null;
		openSession();
		
		session.beginTransaction();
		StringBuilder queryString = new StringBuilder("from TimeChunk tc where ");
		
		buildQuery(queryString, true, false);
		
		queryString.append(" order by tc.startTime");
		Query query = session.createQuery(queryString.toString());
		setQueryVariables(eventId, query, true, false);
		
		List list = query.list();
		
		session.getTransaction().commit();
		
		if (list != null && !list.isEmpty()) {
			List<TimeChunk> temp = (List<TimeChunk>) list;
			result = temp;
			found = true;
		}
		
		else {
			found = false;
		}
		Hibernate.initialize(result);
		if (result != null) {
			for (TimeChunk tc : result) {
				Hibernate.initialize(tc.getClient());
				Hibernate.initialize(tc.getLocation());
			}
		}
		closeSession();
		
		return found ? result : null;
	}
	
	private void setQueryVariables(String id, Query query, boolean byEventID, boolean byChunkId) {
		if (byEventID) {
			query.setParameter("eventId", id);
		}
		if (byChunkId) {
			query.setParameter("chunkId", id);
		}
	}
	
	private void buildQuery(StringBuilder queryString, boolean byEventID, boolean byChunkId) {
		
		boolean moreThanOne = false;
		
		if (byEventID) {
			queryString.append("eventId = :eventId ");
			moreThanOne = true;
		}
		if (byChunkId) {
			if (moreThanOne) {
				queryString.append("AND ");
			}
			queryString.append("chunkId = :chunkId ");
			moreThanOne = true;			
		}
	}
	
	private void updateTimelineInTimeChunk(TimeChunk newTimeChunk) {
		Timeline timeline = new Timeline();
		timeline.setEventId(newTimeChunk.getEventId());
		timeline = timelineManager.getTimelineByEventId(timeline);
		newTimeChunk.setTimeline(timeline);
	}
	
	private String getNextId(TimeChunk newChunk) {
		int nextId = -1;
		List<TimeChunk> foundChunks = new ArrayList<TimeChunk>(); 
		
		openSession();
		
		try {
			session.beginTransaction();
			
			StringBuilder queryString = new StringBuilder("from TimeChunk where ");
			buildQuery(queryString, false, true, false);
			
			Query query = session.createQuery(queryString.toString());
			setQueryVariables(newChunk, query, false, true, false);

			List list = query.list();
			
			session.getTransaction().commit();
			
			if (list != null && !list.isEmpty()) {
				foundChunks = (List<TimeChunk>) list;
				Collections.sort(foundChunks, chunkIdComparator);
				nextId = Integer.valueOf(foundChunks.get(foundChunks.size() - 1).getChunkId());
				nextId++;
			}
			else if (list.isEmpty()) {
				nextId = 1;
			}
		}
		
		catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		closeSession();
		
		return "" + nextId;
	}

	public Set<TimeChunk> getTimeChunksByTimeline(Timeline timeline) {
		Set<TimeChunk> foundChunks = new HashSet<TimeChunk>(); 
		openSession();
		
		try {
			session.beginTransaction();
			
			StringBuilder queryString = new StringBuilder("from TimeChunk where ");
			buildQuery(queryString, false, true, false);
			
			Query query = session.createQuery(queryString.toString());
			setQueryVariables(timeline, query);

			List list = query.list();
			
			session.getTransaction().commit();
			
			if (list != null && !list.isEmpty()) {
//				foundChunks = (Set<TimeChunk>) list;
				foundChunks.addAll(list);
			}
		}
		
		catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		finally {
			Hibernate.initialize(foundChunks);
			for (TimeChunk chunk : foundChunks) {
				Hibernate.initialize(chunk.getPhotographers());
				Hibernate.initialize(chunk.getClient());
				Hibernate.initialize(chunk.getLocation());
				Hibernate.initialize(chunk.getStartTime());
				Hibernate.initialize(chunk.getPosition());
				Hibernate.initialize(chunk.getDurationHr());
				Hibernate.initialize(chunk.getDurationMin());
				Hibernate.initialize(chunk.getDescription());
			}
			closeSession();
		}
		return foundChunks;
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
		return setTimeChunkHelper(timeChunk, newPosition, null, null, null, null, null, null, null, 
				true, false, false, false, false, false, false, false);
	}
	
	public TimeChunk setTimeChunkStartTime(TimeChunk timeChunk, Time newStartTime) {
		return setTimeChunkHelper(timeChunk, null, newStartTime, null, null, null, null, null, null, 
				false, true, false, false, false, false, false, false);
	}
	
	public TimeChunk setTimeChunkLocation(TimeChunk timeChunk, Location newLocation) {
		return setTimeChunkHelper(timeChunk, null, null, newLocation, null, null, null, null, null, 
				false, false, true, false, false, false, false, false);
	}
	
	public TimeChunk setTimeChunkDuration(TimeChunk timeChunk, String newDurationHr, String newDurationMin) {
		return setTimeChunkHelper(timeChunk, null, null, null, newDurationHr, newDurationMin, null, null, null, 
				false, false, false, true, false, false, false, false);
	}
	
	public TimeChunk setTimeChunkDescription(TimeChunk timeChunk, String newDescription) {
		return setTimeChunkHelper(timeChunk, null, null, null, null, null, newDescription, null, null, 
				false, false, false, false, true, false, false, false);
	}
	
	public TimeChunk setTimeChunkClient(TimeChunk timeChunk, Client newClient) {
		return setTimeChunkHelper(timeChunk, null, null, null, null, null, null, newClient, null, 
				false, false, false, false, false, true, false, false);
	}
	
	public TimeChunk setPhotographer(TimeChunk timeChunk, Photographer setPhotographer) {
		Set<Photographer> setPhotographers = new HashSet<Photographer>();
		setPhotographers.add(setPhotographer);
		
		return setPhotographers(timeChunk, setPhotographers); 
	}
	
	public TimeChunk setPhotographers(TimeChunk timeChunk, Set<Photographer> setPhotographers) {
		return setTimeChunkHelper(timeChunk, null, null, null, null, null, null, null, setPhotographers, 
				false, false, false, false, false, false, true, false);
	}
	
	public TimeChunk addPhotographer(TimeChunk timeChunk, Photographer newPhotographer) {
		Set<Photographer> addPhotographers = new HashSet<Photographer>();
		addPhotographers.add(newPhotographer);
		
		return addPhotographers(timeChunk, addPhotographers); 
	}
	
	public TimeChunk addPhotographers(TimeChunk timeChunk, Set<Photographer> newPhotographers) {
		return setTimeChunkHelper(timeChunk, null, null, null, null, null, null, null, newPhotographers, 
				false, false, false, false, false, false, false, true);
	}
	
	public TimeChunk deletePhotographer(TimeChunk timeChunk, Photographer deletePhotoprapher) {
		
		Set<Photographer> deletePhotographers = new HashSet<Photographer>();
		deletePhotographers.add(deletePhotoprapher);
		
		return deletePhotographers(timeChunk, deletePhotographers); 
	}
	
	public TimeChunk deletePhotographers(TimeChunk timeChunk, Set<Photographer> deletePhotopraphers) {
		
		// first try to find the timeChunk
		TimeChunk foundTimeChunk = findTimeChunk(timeChunk, false, true, true);
		if (foundTimeChunk != null) {
			
			// keep old data for reset on fail
			Set<Photographer> oldPhotographers = foundTimeChunk.getPhotographers();
			
			try {
				Set<Photographer> updatedPhotogs = new HashSet<Photographer>(oldPhotographers);
				
				openSession();
				
				session.beginTransaction();
				
				updatedPhotogs.removeAll(deletePhotopraphers);
				foundTimeChunk.setPhotographers(updatedPhotogs);
				
				session.saveOrUpdate(foundTimeChunk);
				
				Hibernate.initialize(foundTimeChunk);
				session.getTransaction().commit();
			}
			catch (HibernateException e) {
				session.getTransaction().rollback();
				foundTimeChunk.setPhotographers(oldPhotographers);
			}
			
			finally {
				session.close();
			}
		}
		
		return foundTimeChunk;
	}
	
	private TimeChunk findTimeChunk(TimeChunk timeChunk, boolean byChunkId, boolean byEventId, boolean byDescription) {
		boolean found = false;

		openSession();
		
		try {
			session.beginTransaction();
			
			StringBuilder queryString = new StringBuilder("from TimeChunk where ");
			System.out.println("chunk search data");
			System.out.println(timeChunk.getChunkId());
			System.out.println(timeChunk.getTimeline());
			buildQuery(queryString, byChunkId, byEventId, byDescription);
			Query query = session.createQuery(queryString.toString());
			setQueryVariables(timeChunk, query, byChunkId, byEventId, byDescription);

			List list = query.list();
			session.getTransaction().commit();
			
			if (list != null && !list.isEmpty()) {
				TimeChunk temp = (TimeChunk) list.get(0);
				Hibernate.initialize(temp);
				Hibernate.initialize(temp.getEventId());
				Hibernate.initialize(temp.getClient());
				Hibernate.initialize(temp.getDescription());
				Hibernate.initialize(temp.getDurationHr());
				Hibernate.initialize(temp.getDurationMin());
				Hibernate.initialize(temp.getLocation());
				Hibernate.initialize(temp.getPhotographers());
				Hibernate.initialize(temp.getPosition());
				Hibernate.initialize(temp.getStartTime());
				Hibernate.initialize(temp.getTimeline());
				Hibernate.initialize(temp.getNotes());
//				System.out.println("found one: " + temp.toString());
				timeChunk = temp;
				found = true;
				System.out.println("Notes:");
				System.out.println(temp.getNotes());
			}
			
			else {
				found = false;
				System.out.println("no chunk exists");
			}
		}
		
		catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		finally {
			Hibernate.initialize(timeChunk);
			closeSession();
		}
		
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
			query.setParameter("eventid", timeChunk.getTimeline().getEventId());
		}
		if (byDescription) {
			query.setParameter("description", timeChunk.getDescription());
		}
		
	}
	
	private void setQueryVariables(Timeline timeline, Query query) {
		query.setParameter("eventid", timeline.getEventId());
	}
	
	private TimeChunk setTimeChunkHelper(TimeChunk timeChunk, Integer newPosition, Time newStartTime, Location newLocation, String newDurationHr, String newDurationMin,
			String newDescription, Client newClient, Set<Photographer> newPhotographers, boolean updatePosition, boolean updateStartTime, boolean updateLocation, boolean updateDuration,
			boolean updateDescription, boolean updateClient, boolean setPhotographers, boolean addPhotographers) {
		
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
//		Time oldDuration = timeChunk.getDuration();
		String oldDurationHr = timeChunk.getDurationHr();
		String oldDurationMin = timeChunk.getDurationMin();
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
			newDurationHr = oldDurationHr;
			newDurationMin = oldDurationMin;
		}
		if (!updateDescription) {
			newDescription = oldDescription;
		}
		if (!updateClient) {
			newClient = oldClient;
		}
		
		if (!setPhotographers || !addPhotographers) {
			if (addPhotographers) {
				// add old to new
				newPhotographers.addAll(oldPhotographers);
			}
			
			else if (setPhotographers) {
				// do nothing
			}
			else {
				newPhotographers = oldPhotographers;
			}
		}
		
		// first try to find the timeChunk
		TimeChunk foundTimeChunk = findTimeChunk(timeChunk, false, true, true);
		
		TimeChunk foundTimeChunkConflict = null;
		
//		// if updating the startTime, check if the new startTime already exists
//		if (updateStartTime) {
//			foundTimeChunkConflict = new TimeChunk(timeChunk.getChunkId(), timeChunk.getTimeline(), timeChunk.getPosition(),
//					newStartTime, timeChunk.getLocation(), timeChunk.getDuration(), timeChunk.getDescription(), 
//					timeChunk.getClient(), timeChunk.getPhotographers());
//			
//			foundTimeChunkConflict = findTimeChunk(foundTimeChunkConflict, false, true, true);
//			
//			if (foundTimeChunkConflict != null) {
//				throw new EntityExistsException("Cannot have tow chunks at the same time, add code to offe user to shift all timechunks: " + foundTimeChunkConflict.toString());
//				/*
//				 * set offset time to the difference in overlap
//				 * 
//				 * get all chunks by timeline id for the timeline
//				 * 
//				 * starting at the latest chunk, add the offest to the chunk's starttime. 
//				 */
//			}
//		}
		
		// If the Timechunk exists, Update the timeChunk and set old parameters to new parameters
		try {
			if (foundTimeChunk != null && foundTimeChunkConflict == null) {
				
				foundTimeChunk.setPosition(newPosition);
				foundTimeChunk.setStartTime(newStartTime);
				foundTimeChunk.setLocation(newLocation);
				foundTimeChunk.setDurationHr(newDurationHr);
				foundTimeChunk.setDurationMin(newDurationMin);
				foundTimeChunk.setDescription(newDescription);
				foundTimeChunk.setClient(newClient);
				foundTimeChunk.setPhotographers(newPhotographers);
				
				// update the client id and new last name
				timeChunk.setPosition(newPosition);
				timeChunk.setStartTime(newStartTime);
				timeChunk.setLocation(newLocation);
				timeChunk.setDurationHr(newDurationHr);
				timeChunk.setDurationMin(newDurationMin);
				timeChunk.setDescription(newDescription);
				timeChunk.setClient(newClient);
				timeChunk.setPhotographers(newPhotographers);
				
				// open new session and commit
				openSession();
					
				session.beginTransaction();
				Hibernate.initialize(foundTimeChunk);
				
				// if adding a location, first check if it already exists
				if (updateLocation) {
					Location foundLocation = locationManager.getLocationByAddress(newLocation);
					if (foundLocation != null) {
						timeChunk.setLocation(foundLocation);
						foundTimeChunk.setLocation(foundLocation);
					}
				}
				
				session.saveOrUpdate(foundTimeChunk);
				session.getTransaction().commit();
				
				// persist to avoid lazy error
				Hibernate.initialize(timeChunk);
				Hibernate.initialize(timeChunk.getStartTime());
				if (updateClient) {
					Hibernate.initialize(timeChunk.getClient());
				}
//				if (updateLocation) {
					Hibernate.initialize(timeChunk.getLocation());
					Hibernate.initialize(foundTimeChunk.getLocation());
//				}
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
				timeChunk.setDurationHr(oldDurationHr);
				timeChunk.setDurationMin(oldDurationMin);
				timeChunk.setDescription(oldDescription);
				timeChunk.setClient(oldClient);
				timeChunk.setPhotographers(oldPhotographers);
			}
		}
		return valid ? timeChunk : null;
	}
	
	public TimeChunk DeleteTimeChunkById(TimeChunk timeChunk) {
		boolean deleted = false;
		
		TimeChunk foundTimeChunk = null;
		// check if in database
		foundTimeChunk = findTimeChunk(timeChunk, true, true, false);
		
		if (foundTimeChunk != null) {
			deleted = deleteTimeChunk(foundTimeChunk);
		}

		// update the mileage
		if (deleted) {		
			mileageManager.updateEventMileage(timeChunk);
		}
		
		return deleted? foundTimeChunk : null;
	}
	
	// helper method to delete from database. returns success / failure status
	private boolean deleteTimeChunk(TimeChunk timeChunkToDelete) {
		boolean success = false;
		try {
			openSession();
			
			session.beginTransaction();
			session.delete(timeChunkToDelete);
			session.getTransaction().commit();
			closeSession();
			success = true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return success;
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
	
	public Comparator<TimeChunk> chunkIdComparator = new Comparator<TimeChunk>() {
		
		public int compare(TimeChunk o1, TimeChunk o2) {
			return Integer.valueOf(o1.getChunkId()).compareTo(Integer.valueOf(o2.getChunkId()));
		}
	};
}
