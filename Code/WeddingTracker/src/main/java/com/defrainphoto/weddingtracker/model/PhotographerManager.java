package main.java.com.defrainphoto.weddingtracker.model;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.hibernate.Query;
import org.hibernate.Session;

public class PhotographerManager {
	Session session;
	
	public Photographer addPhotographer(Photographer newPhotographer) {

		boolean found = false;
		Photographer temp = findPhotographer(newPhotographer, false, true, true);
		
		// if already in DB, throw exception
		if (temp != null) {
			throw new EntityExistsException("Entity already Exists:  " + newPhotographer .toString());
		}
		
		// else, open session, save, and commit
		else {
			openSession();
			
			session.beginTransaction();
			session.save(newPhotographer);
			session.getTransaction().commit();
			
			temp = findPhotographer(newPhotographer, false, true, true);
			newPhotographer.setStaffId(temp.getStaffId()); 
			closeSession();
			found = true;
		}
		
		return found ? newPhotographer : null;
	}

	public Photographer getPhotographerbyName(Photographer photog) {
		Photographer result;
		
		result = findPhotographer(photog, false, true, true);
		return result; 
	}
	
	public Photographer getPhotographerById(Photographer photog) {
		Photographer result;
		
		result = findPhotographer(photog, true, false, false);
		return result; 
	}
	
	private Photographer findPhotographer(Photographer photog, boolean byID, boolean byFName, boolean byLname) {
		boolean found = false;
		openSession();
		
		session.beginTransaction();
		
		StringBuilder queryString = new StringBuilder("from Photographer where ");
		buildQuery(queryString, byID, byFName, byLname);
		
		Query query = session.createQuery(queryString.toString());
		System.out.println(query.toString());
		setQueryVariables(photog, query, byID, byFName, byLname);
		
		List list = query.list();
		
		session.getTransaction().commit();
		
		if (list != null && !list.isEmpty()) {
			Photographer temp = (Photographer) list.get(0);
			photog.setStaffId(temp.getStaffId());
			found = true;
		}
		
		else {
			System.out.println("yep, here");
			found = false;
		}
		
		closeSession();
		
		return found ? photog : null;
	}
	
	private void setQueryVariables(Photographer photog, Query query, boolean byID, boolean byFName, boolean byLname) {
		if (byID) {
			query.setParameter("staffId", photog.getStaffId());
		}
		if (byFName) {
			query.setParameter("fname", photog.getFirstName());
		}
		if (byLname) {
			query.setParameter("lname", photog.getLastName());
		}
		
	}

	private void buildQuery(StringBuilder queryString, boolean byID, boolean byFName, boolean byLname) {
		boolean moreThanOne = false;
		
		if (byID) {
			queryString.append("staffId = :staffId ");
			moreThanOne = true;
		}
		if (byFName) {
			if (moreThanOne) {
				queryString.append("AND ");
			}
			queryString.append("firstName = :fname ");
			moreThanOne = true;			
		}
		
		if (byLname) {
			if (moreThanOne) {
				queryString.append("AND ");
			}
			queryString.append("lastName = :lname ");
			moreThanOne = true;
		}		
	}

	/**
	 * Method to update a photographer's first name by searching the existing data by first / last name.
	 * If found, the photog is updated with the new first name
	 * 
	 * @param photog The <code>main.java.com.defrainphoto.weddingTracker.model.Photographer</code> to be found
	 * @param newFirstName the new first name of the photographer
	 * @param session the current session with the DB
	 * 
	 * @return  <code>true</code> if found, else <code>false</code>
	 */

	public Photographer setPhotographerFirstName(Photographer photog, String newFirstName) {
		
		// first try to find the photographer
		Photographer foundPhotog = findPhotographer(photog, false, true, true);
		
		// also check if new name already exists
		Photographer foundPhotogNewName = new Photographer(null, newFirstName, photog.getLastName());
		foundPhotogNewName = findPhotographer(foundPhotogNewName, false, true, true);
		
		if (foundPhotogNewName != null) {
			throw new EntityExistsException("new entity already exists: " + foundPhotogNewName.toString());
		}
		// if found, update
		boolean valid = false;
		
		String oldFirstName = photog.getFirstName();
		
		// if found, update the current photog and set name to new name
		try {
			if (foundPhotog != null && foundPhotogNewName == null) {
				foundPhotog.setFirstName(newFirstName);
				
				// update the photog id and new first name
				photog.setStaffId(foundPhotog.getStaffId());
				photog.setFirstName(newFirstName);
				
				// open new session and commit
				
				openSession();
				
				session.beginTransaction();
				session.saveOrUpdate(foundPhotog);
				session.getTransaction().commit();
				
				closeSession();
				
				valid = true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		finally {
			
			// if not valid, reset to old name
			if (!valid) {
				photog.setFirstName(oldFirstName);
			}
		}
		return valid ? photog : null;
	}
	
	
	public Photographer setPhotographerLastName(Photographer photog, String newLastName) {
		// first try to find the photographer
				Photographer foundPhotog = findPhotographer(photog, false, true, true);
				
				// also check if new name already exists
				Photographer foundPhotogNewName = new Photographer(null, photog.getFirstName(), newLastName);
				foundPhotogNewName = findPhotographer(foundPhotogNewName, false, true, true);
				
				if (foundPhotogNewName != null) {
					throw new EntityExistsException("new entity already exists: " + foundPhotogNewName.toString());
				}
				// if found, update
				boolean valid = false;
				
				String oldLastName = photog.getLastName();
				
				// if found, update the current photog and set name to new name
				try {
					if (foundPhotog != null && foundPhotogNewName == null) {
						foundPhotog.setLastName(newLastName);
						
						// update the photog id and new last name
						photog.setStaffId(foundPhotog.getStaffId());
						photog.setLastName(newLastName);
						
						// open new session and commit
						
						openSession();
						
						session.beginTransaction();
						session.saveOrUpdate(foundPhotog);
						session.getTransaction().commit();
						
						closeSession();
						
						valid = true;
					}
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				
				finally {
					
					// if not valid, reset to old name
					if (!valid) {
						photog.setLastName(oldLastName);
					}
				}
				return valid ? photog : null;
	}
	
	private void openSession() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	private void closeSession() {
		session.close();
	}
}
