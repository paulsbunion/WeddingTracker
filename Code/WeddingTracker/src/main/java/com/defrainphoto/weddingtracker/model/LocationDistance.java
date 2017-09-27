package com.defrainphoto.weddingtracker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="LOCATIONDISTANCE")
public class LocationDistance {
	@Id 
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name= "FROMLOCID")
	private Location fromLocation;
	
	@ManyToOne
	@JoinColumn(name= "TOLOCID")
	private Location toLocation;
	
	@Column(name="MILES")
	private String miles;
}