package com.sheffield.studentcooking.beans;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Bean implementation class Recipe
 */
@SuppressWarnings("serial")
public class RecipeBean implements Serializable {
	
	private int id;
	private String name;
    private String description;
    private String photo;
    private String time;
    private Timestamp created_at;
    
    /**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the description of the event
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the photo
	 */
	public String getPhoto() {
		return photo;
	}
	
	/**
	 * @param photo the photo to set
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the created_at
	 */
	public Timestamp getCreated_at() {
		return created_at;
	}
	
	/**
	 * @param created_at the created_at to set
	 */
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
}
