package com.defrainphoto.weddingtracker.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

import javax.persistence.EntityExistsException;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.json.JSONObject;
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
			String newDistance = getGoogleDistance(fromLocation, toLocation);
			
//			newDistance = "-1.0";
			
			LocationDistance locationDistance = new LocationDistance(fromLocation, toLocation, newDistance);
			addLocationDistance(locationDistance);
			
			distance = Double.parseDouble(newDistance);
		}
		
		closeSession();
		
		return distance;
	}

	private String getGoogleDistance(Location fromLocation, Location toLocation) {
		// TODO Auto-generated method stub
		String result = "-1.0";
		
		StringBuilder fromBuilder = new StringBuilder("origins=");
		fromBuilder.append(fromLocation.toGoogleMapString());
		
		StringBuilder toBuilder = new StringBuilder("&destinations=");
		toBuilder.append(toLocation.toGoogleMapString());
		
		String urlString = MileageManager.mapsAPIURL + MileageManager.responseType + fromBuilder.toString() +
				toBuilder.toString() + MileageManager.units + MileageManager.apiKey;
		
		System.out.println("url:");
		System.out.println(urlString);
		
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			int responseCode = connection.getResponseCode();
			System.out.println("Response code: " + responseCode);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			System.out.println("the response:");
			System.out.println(response.toString());
			
			JSONObject jsonObj = new JSONObject(response.toString());
			JSONObject distObj = jsonObj.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0);
			Integer distanceString = distObj.getJSONObject("distance").getInt("value");
			
			System.out.println("the found value: ");
			System.out.println(distanceString);
			// convert the distance in meters to miles
//			double distance = Double.parseDouble(distanceString);
			double distance = 0.0 + distanceString;
			distance *= 0.000621371;
			
			DecimalFormat df2 = new DecimalFormat(".##");
			
			result = df2.format(distance);
		} 
		
		catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
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
