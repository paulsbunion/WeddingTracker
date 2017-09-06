package com.defrainphoto.weddingtracker.model;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
public class UserManager {

	private Session session;
	
	public User getUserByUserName(String userName) {
		User result = new User();
		result.setUserName(userName);
		
		result = findUser(result, true, false);
		return result; 
	}
	
	private User findUser(User user, boolean byUserName, boolean byEmail) {
		boolean found = false;
		openSession();
		
		session.beginTransaction();
		
		StringBuilder queryString = new StringBuilder("from User where ");
		buildQuery(queryString, byUserName, byEmail, false);
		
		Query query = session.createQuery(queryString.toString());
		setQueryVariables(user, query, byUserName, byEmail, false);
		List list = query.list();
		
		session.getTransaction().commit();
		System.out.println(list.toString());
		if (list != null && !list.isEmpty()) {
			User temp = (User) list.get(0);
			//client.setClientId(temp.getClientId());
			user = temp;
			found = true;
		}
		
		else {
			found = false;
		}
		Hibernate.initialize(user);
		closeSession();
		if (found) {
			System.out.println("found");
			System.out.println(user);
		}
		return found ? user : null;
	}
	
	public List<UserRole> getUserRoles(User user) {
		List<UserRole> result = null;
		System.out.println("getting user roles");
		result = (List<UserRole>) findUserRoles(user, false, true);
		
		return result;
	}
	
	private List findUserRoles(User user, boolean byRoleId, boolean byUserName) {
		List<UserRole> userRoles = null;
		boolean found = false;
		openSession();
		
		session.beginTransaction();
		
		StringBuilder queryString = new StringBuilder("from UserRole where ");
		buildQuery(queryString, byUserName, false, byRoleId);
		
		Query query = session.createQuery(queryString.toString());
		setQueryVariables(user, query, byUserName, false, byRoleId);
		
		List list = query.list();
		
		session.getTransaction().commit();
		
		if (list != null && !list.isEmpty()) {
			List<UserRole> temp = (List<UserRole>) list;
			userRoles = temp;
			found = true;
		}
		
		else {
			found = false;
		}
		Hibernate.initialize(userRoles);
		closeSession();
		
		return found ? userRoles : null;
	}
		
	
	private void setQueryVariables(User user, Query query, boolean byUserName, boolean byEmail, boolean byRoleId) {
		if (byUserName) {
			query.setParameter("userName", user.getUserName());
		}
		if (byEmail) {
			query.setParameter("email", user.getEmail());
		}
		if (byRoleId) {
			query.setParameter("user_Role_Id", user.getEmail());
		}
		
	}

	private void buildQuery(StringBuilder queryString, boolean byUserName, boolean byEmail, boolean byRoleId) {
		
		boolean moreThanOne = false;
		
		if (byUserName) {
			queryString.append("userName = :userName ");
			moreThanOne = true;
		}
		if (byEmail) {
			if (moreThanOne) {
				queryString.append("AND ");
			}
			queryString.append("email = :email ");
			moreThanOne = true;			
		}
		if (byRoleId) {
			if (moreThanOne) {
				queryString.append("AND ");
			}
			queryString.append("user_Role_Id = :user_Role_Id ");
			moreThanOne = true;			
		}
	}

	private void openSession() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	private void closeSession() {
		session.close();
	}

	
}
