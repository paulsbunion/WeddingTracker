package test.java.com.defrainphoto.weddingtracker.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import main.java.com.defrainphoto.weddingtracker.model.Location;
import main.java.com.defrainphoto.weddingtracker.model.LocationManager;
import main.java.com.defrainphoto.weddingtracker.model.HibernateUtil;

public class LocationManagerTest {
	Session session;
	Location location;
	LocationManager locationManager;	
	
	@Before
	public void setUp() {
		locationManager = new LocationManager();
	}
	
	@Test
	public void testAddLocation() {
		List<Location> locationList = new LinkedList<Location>();
		location = new Location(null, "Pickerington", "OH", "43147", "123 E. Main St.", "home");
		locationList.add(location);
		
		locationManager.addLocation(location);
		
		Location retreived;
		retreived = locationManager.getLocationByAddress(location);
		
		assertEquals("did not add Location", retreived, location);
		
		// clean up
		cleanUp(locationList);
	}

	@Test
	public void testUpdateLocationById() {
		
	}
	
	@Test
	public void testUpdateLocationCity() {
		List<Location> locationList = new ArrayList<Location>();
		
		try {
			location = new Location(null, "Pickerington", "OH", "43147", "123 E. Main St.", "home");
			locationList.add(location);
			
			locationManager.addLocation(location);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Location updatedLocation = null;

		try {
			updatedLocation = locationManager.setLocationCity(location, "no longer Pickerington");
			locationList.add(updatedLocation);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Location newLocation = locationManager.getLocationById(location);
		
		assertTrue("Did not update City", updatedLocation != null);
		assertEquals("Entities are not the same" + location.toString() + " vs. " + newLocation.toString(),
				location.getCity(), newLocation.getCity());
		
		// clean up
		cleanUp(locationList);
	}
	
	@Test
	public void testUpdateLocationState() {
		List<Location> locationList = new ArrayList<Location>();
		
		try {
			location = new Location(null, "Pickerington", "OH", "43147", "123 E. Main St.", "home");
			locationList.add(location);
			
			locationManager.addLocation(location);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Location updatedLocation = null;

		try {
			System.out.println("old location");
			System.out.println(location);
			updatedLocation = locationManager.setLocationState(location, "MI");
			locationList.add(updatedLocation);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Location newLocation = locationManager.getLocationById(location);
		
		assertTrue("Did not update State", updatedLocation != null);
		assertEquals("Entities are not the same" + location.toString() + " vs. " + newLocation.toString(),
				location.getState(), newLocation.getState());
		
		// clean up
		cleanUp(locationList);
	}
	
	@Test
	public void testUpdateLocationZip() {
		List<Location> locationList = new ArrayList<Location>();
		
		try {
			location = new Location(null, "Pickerington", "OH", "43147", "123 E. Main St.", "home");
			locationList.add(location);
			
			locationManager.addLocation(location);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Location updatedLocation = null;

		try {
			updatedLocation = locationManager.setLocationZip(location, "90210");
			locationList.add(updatedLocation);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Location newLocation = locationManager.getLocationById(location);
		
		assertTrue("Did not update Zip", updatedLocation != null);
		assertEquals("Entities are not the same" + location.toString() + " vs. " + newLocation.toString(),
				location.getCity(), newLocation.getCity());
		assertEquals("Entities are not the same" + location.toString() + " vs. " + newLocation.toString(),
				location.getState(), newLocation.getState());
		
		// clean up
		cleanUp(locationList);
	}
	
	@Test
	public void testUpdateLocationStreet() {
		List<Location> locationList = new ArrayList<Location>();
		
		try {
			location = new Location(null, "Pickerington", "OH", "43147", "123 E. Main St.", "home");
			locationList.add(location);
			
			locationManager.addLocation(location);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Location updatedLocation = null;

		try {
			updatedLocation = locationManager.setLocationStreet(location, "new Address!");
			locationList.add(updatedLocation);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Location newLocation = locationManager.getLocationById(location);
		
		assertTrue("Did not update Street", updatedLocation != null);
		assertEquals("Entities are not the same" + location.toString() + " vs. " + newLocation.toString(),
				location.getStreet(), newLocation.getStreet());
		
		// clean up
		cleanUp(locationList);
	}
	
	@Test
	public void testUpdateLocationDescription() {
		List<Location> locationList = new ArrayList<Location>();
		
		try {
			location = new Location(null, "Pickerington", "OH", "43147", "123 E. Main St.", "home");
			locationList.add(location);
			
			locationManager.addLocation(location);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Location updatedLocation = null;

		try {
			updatedLocation = locationManager.setLocationDescription(location, "new Description");
			locationList.add(updatedLocation);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Location newLocation = locationManager.getLocationById(location);
		
		assertTrue("Did not update Description", updatedLocation != null);
		assertEquals("Entities are not the same" + location.toString() + " vs. " + newLocation.toString(),
				location.getDescription(), newLocation.getDescription());
		
		// clean up
		cleanUp(locationList);
	}
	
	
	
	@Test
	public void testFindLocationById() {
		List<Location> locationList = new ArrayList<Location>();
		
		try {
			location = new Location("1", "Pickerington", "OH", "43147", "123 E. Main St.", "home");
			locationList.add(location);
			
			location = locationManager.addLocation(location);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Location foundLocation = null;

		try {
			foundLocation = locationManager.getLocationById(location);
			locationList.add(foundLocation);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		assertEquals("Did not find Location by Id", location, foundLocation);
		
		// clean up
		cleanUp(locationList);
	}
	
	@Test
	public void testFindLocationByAddress() {
		List<Location> locationList = new ArrayList<Location>();
		
		try {
			location = new Location(null, "Pickerington", "OH", "43147", "123 E. Main St.", "home");
			locationList.add(location);
			
			locationManager.addLocation(location);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Location foundLocation = null;

		try {
			foundLocation = locationManager.getLocationByAddress(location);
			locationList.add(foundLocation);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		assertEquals("Did not find Location by Address", location, foundLocation);
		
		// clean up
		cleanUp(locationList);
	}
	
	
	
	@Test
	public void testDeleteLocationByAddress() {
		List<Location> locationList = new ArrayList<Location>();
		
		Location deleteMe1 = new Location(null, "Pickerington", "OH", "43147", "123 E. Main St.", "home");
		locationList.add(deleteMe1);
		locationManager.addLocation(deleteMe1);
		
		Location deletedLocation = null;
		try {
			deleteMe1.setLocationId(locationManager.getLocationByAddress(deleteMe1).getLocationId());
			deletedLocation = locationManager.deleteLocationByAddress(deleteMe1);
			locationList.add(deletedLocation);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		
		// try to get deleted photographer
		Location newLocation = locationManager.getLocationByAddress(deleteMe1);
		if (newLocation != null) {
			locationList.add(newLocation);
		}
		
		assertTrue("Did not Delete Location ", newLocation == null);
		assertEquals("Wrong return from delete", deleteMe1, deletedLocation);
		
		// clean up
		cleanUp(locationList);
	}
	
	@Test
	public void testDeleteLocationById() {
		List<Location> locationList = new ArrayList<Location>();
		
		Location deleteMe1 = new Location(null, "Pickerington", "OH", "43147", "123 E. Main St.", "home");
		locationList.add(deleteMe1);
		locationManager.addLocation(deleteMe1);
		
		Location deletedLocation = null;
		try {
			deleteMe1.setLocationId(locationManager.getLocationById(deleteMe1).getLocationId());
			deletedLocation = locationManager.deleteLocationById(deleteMe1);
			locationList.add(deletedLocation);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		
		// try to get deleted photographer
		Location newLocation = locationManager.getLocationById(deleteMe1);
		if (newLocation != null) {
			locationList.add(newLocation);
		}
		
		assertTrue("Did not Delete Location ", newLocation == null);
		assertEquals("Wrong return from delete", deleteMe1, deletedLocation);
		
		// clean up
		cleanUp(locationList);
	}
	
	private void openSession() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	private void closeSession() {
		session.close();
	}
	
	private void cleanUp(List<Location> locationList) {
		// TODO Auto-generated method stub
		Iterator<Location> LocationIterator = locationList.iterator();
		Location temp;
		while (LocationIterator.hasNext()) {
			temp = LocationIterator.next();
			try {
				locationManager.deleteLocationByAddress(temp);
			}
			catch (Exception e) {
				// do nothing
			}
		}
	}
}
