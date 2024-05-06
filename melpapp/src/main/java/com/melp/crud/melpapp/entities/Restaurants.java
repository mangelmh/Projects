package com.melp.crud.melpapp.entities;

import lombok.Data;
import javax.persistence.*;
import javax.persistence.Table;

@Data
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
    private double lat;
    private double lng;

    /*@Column(columnDefinition = "geometry(Point,4326)")
    private Point location;

    public void setLocation(double latitude, double longitude) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        this.location = geometryFactory.createPoint(new Coordinate(longitude, latitude));
    } */
}