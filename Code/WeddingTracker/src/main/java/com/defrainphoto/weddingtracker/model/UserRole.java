package com.defrainphoto.weddingtracker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ROLES")
public class UserRole {
	@Id
	private int user_Role_Id;
	
	@Column
	private String userName;
	
	@Column
	private String role;
	
//	@ManyToOne
//	private User user;

	public int getUser_Role_Id() {
		return user_Role_Id;
	}

	public void setUser_Role_Id(int user_Role_Id) {
		this.user_Role_Id = user_Role_Id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + user_Role_Id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRole other = (UserRole) obj;
		if (user_Role_Id != other.user_Role_Id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserRole [userRoleId=" + user_Role_Id + ", userName=" + userName + ", role=" + role + "]";
	}
	
	
}
