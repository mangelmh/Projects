package com.melp.crud.melpapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melp.crud.melpapp.entities.Restaurants;
import com.melp.crud.melpapp.repository.RestaurantsRepository;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/restaurants")
public class RestaurantsRestController {

    @Autowired
    RestaurantsRepository restaurantsRepository;

    @GetMapping()
    public List<Restaurants> findAll() {
        return restaurantsRepository.findAll();
    }

    
}
