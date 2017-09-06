package com.defrainphoto.weddingtracker.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="USERS")
public class User implements Serializable{
 
    private static final long serialVersionUID = 1L;

	@Id
    private String userName;
         
    @NotEmpty
    @Column(name="PASSWORD", nullable=false)
    private String password;
 
    @Column(name="ENABLED", nullable=false)
    private boolean enabled;
 
    @NotEmpty
    @Column(name="EMAIL", nullable=false)
    private String email;
    
    @OneToMany
    @JoinColumn(name="USERNAME")
    private Set<UserRole> userRoles = new HashSet<UserRole>();
    
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "USER_ROLES", 
//    		joinColumns = { @JoinColumn(name = "USERNAME")})
////    		joinColumns = { @JoinColumn(name = "USERNAME")}, 
////    		inverseJoinColumns = { @JoinColumn(name = "USERNAME")})
//    private Set<UserRole> userRoles = new HashSet<UserRole>();

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
}
