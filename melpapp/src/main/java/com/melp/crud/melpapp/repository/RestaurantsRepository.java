package com.melp.crud.melpapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.melp.crud.melpapp.entities.Restaurants;


public interface RestaurantsRepository extends JpaRepository<Restaurants,String>{
	
	@Query(value = "SELECT COUNT(*) FROM restaurant r WHERE ST_DWithin(ST_SetSRID(ST_MakePoint(r.lng, r.lat)::geography, 4326), ST_SetSRID(ST_MakePoint(?longitude, ?latitude)::geography, 4326), ?radius)", nativeQuery = true)
	int countRestaurantsWithinCircle(@Param("latitude") float latitude, @Param("longitude") float longitude, @Param("radius") float radius);


}
