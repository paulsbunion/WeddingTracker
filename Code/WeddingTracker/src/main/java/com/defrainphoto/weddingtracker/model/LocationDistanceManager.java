package com.defrainphoto.weddingtracker.model;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
public class LocationDistanceManager {

	Session session;
	
	public double getDistance(Location lastLocation, Location nextLocation) {
		double distance = -1.0;
		Integer fromId = Integer.parseInt(lastLocation.getLocationId());
		Integer toId = Integer.parseInt(nextLocation.getLocationId());
		
		Location tempLocation;
		
		if (toId < fromId) {
			tempLocation = nextLocation;
			nextLocation = lastLocation;
			lastLocation = tempLocation;			
		}
		
		distance = getDistanceHelper(lastLocation, nextLocation);
		
		return distance;
	}

	private double getDistanceHelper(Location fromLocation, Location toLocation) {
		double distance = -1.0;
		openSession();
		
		session.beginTransaction();
		
		StringBuilder queryString = new StringBuilder("from LocationDistance where ");
		queryString.append("fromLocation = :fromLocation");
		queryString.append(" AND ");
		queryString.append("toLocation = :toLocation");
		
		Query query = session.createQuery(queryString.toString());
		
		System.out.println("the location from / to ids");
		System.out.println(fromLocation.getLocationId());
		System.out.println(toLocation.getLocationId());
		query.setParameter("fromLocation", fromLocation);
		query.setParameter("toLocation", toLocation);
		
		List list = query.list();
		
		session.getTransaction().commit();
		
		if (list != null && !list.isEmpty()) {
			System.out.println("the list");
			System.out.println(list);
			LocationDistance temp = (LocationDistance) list.get(0);
			Hibernate.initialize(temp);
			distance = Double.parseDouble(temp.getMiles());
		}
		// if the list is null, the entry needs added to the database
		else {
			//TODO: use JSON GoogleMaps API to get distance
			String newDistance = "-1.0";
			
			LocationDistance locationDistance = new LocationDistance(fromLocation, toLocation, newDistance);
			addLocationDistance(locationDistance);
			
			distance = Double.parseDouble(newDistance);
		}
		
		closeSession();
		
		return distance;
	}

	private LocationDistance addLocationDistance(LocationDistance newLocationDistance) {
		boolean found = false;
		LocationDistance temp = findLocationDistance(newLocationDistance, false, true);
		
		// if already in DB, throw exception
		if (temp != null) {
			throw new EntityExistsException("Entity already Exists:  " + newLocationDistance.toString());
		}
		
		// else, open session, save, and commit
		else {
			openSession();
			
			session.beginTransaction();
			session.save(newLocationDistance);
			session.getTransaction().commit();
			
			temp = findLocationDistance(newLocationDistance, false, true);
			newLocationDistance.setId(temp.getId()); 
			Hibernate.initialize(newLocationDistance);
			closeSession();
			found = true;
		}
		
		return found ? newLocationDistance : null;
	}
	
	private LocationDistance findLocationDistance(LocationDistance locationDistance, boolean byId, boolean byLocations) {
		boolean found = false;
		openSession();
		
		session.beginTransaction();
		
		StringBuilder queryString = new StringBuilder("from LocationDistance where ");
		buildQuery(queryString, false, true);
		
		Query query = session.createQuery(queryString.toString());
		setQueryVariables(locationDistance, query, false, true);
		
		List list = query.list();
		
		session.getTransaction().commit();
		System.out.println("In locationManager");
		System.out.println(list);
		if (list != null && !list.isEmpty()) {
			LocationDistance temp = (LocationDistance) list.get(0);
			locationDistance = temp;
//			location.setLocationId(temp.getLocationId());
			System.out.println(locationDistance);
			found = true;
		}
		
		else {
			found = false;
		}
		Hibernate.initialize(locationDistance);
		closeSession();
		
		return found ? locationDistance : null;
	}
	
	private void buildQuery(StringBuilder queryString, boolean byID, boolean byLocations) {
		
		if (byID) {
			queryString.append("id = :locationId ");
		}
		if (byLocations) {
			queryString.append("fromLocation = :fromLocationId");
			queryString.append(" AND ");
			queryString.append("toLocation = :toLocationId");
		}
	}

	private void setQueryVariables(LocationDistance locationDistance, Query query, boolean byID, boolean byLocations) {
		if (byID) {
			query.setParameter("locationId", locationDistance.getId());
		}
		if (byLocations) {
			query.setParameter("fromLocationId" , locationDistance.getFromLocation());
			query.setParameter("toLocationId" , locationDistance.getToLocation());
		}
	}

	private void openSession() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	private void closeSession() {
		session.close();
	}
}
