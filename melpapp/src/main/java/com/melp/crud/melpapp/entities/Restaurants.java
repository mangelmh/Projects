package com.melp.crud.melpapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurant")
public class Restaurants {

	@Id
    private String id;
	
    private int rating;
    private String name;
    private String site;
    private String email;
    private String phone;
    private String street;
    private String city;
    private String state;
    private float lat;
    private float lng;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(float lng) {
		this.lng = lng;
	}	

}