package com.melp.crud.melpapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melp.crud.melpapp.entities.Restaurants;

public interface RestaurantsRepository extends JpaRepository<Restaurants,String>{
}
