package com.melp.crud.melpapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;


import com.melp.crud.melpapp.entities.Restaurants;
import com.melp.crud.melpapp.repository.RestaurantsRepository;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantsController {

	@Autowired
    RestaurantsRepository restaurantsRepository;

    @GetMapping()
    public List<Restaurants> findAll() {
        return restaurantsRepository.findAll();
    }
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    
    @PostMapping("/insert")
    public ResponseEntity<String> insertCsvFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a CSV file.");
        }
        try (Reader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)) {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            List<Restaurants> restaurantsList = StreamSupport.stream(csvParser.spliterator(), false)
                .map(record -> {
                    Restaurants restaurant = new Restaurants();
                    restaurant.setId(record.get("id"));
                    restaurant.setRating(Integer.parseInt(record.get("rating")));
                    restaurant.setName(record.get("name"));
                    restaurant.setSite(record.get("site"));
                    restaurant.setEmail(record.get("email"));
                    restaurant.setPhone(record.get("phone"));
                    restaurant.setStreet(record.get("street"));
                    restaurant.setCity(record.get("city"));
                    restaurant.setState(record.get("state"));
                    restaurant.setLat(Float.parseFloat(record.get("lat")));
                    restaurant.setLng(Float.parseFloat(record.get("lng")));
                    return restaurant;
                })
                .collect(Collectors.toList());

            restaurantsRepository.saveAll(restaurantsList);
            return ResponseEntity.ok("Data successfully uploaded and saved.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }
    
    @PutMapping("/update/{id}")
	public ResponseEntity<?> put(@PathVariable String id, @RequestBody Restaurants input){
		Optional<Restaurants> optionalcustomer = restaurantsRepository.findById(id);
		if(optionalcustomer.isPresent()){
			Restaurants newRestaurants = optionalcustomer.get();
			newRestaurants.setRating(input.getRating());
			newRestaurants.setName(input.getName());
			newRestaurants.setSite(input.getSite());
			newRestaurants.setEmail(input.getEmail());
			newRestaurants.setState(input.getState());
			Restaurants save = restaurantsRepository.save(newRestaurants);
			return new ResponseEntity<>(save,HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
    
    @DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable String id){
    	restaurantsRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
    
    @GetMapping("/restaurants/statistics")
    public ResponseEntity<?> findNearbyRestaurants(
            @RequestParam float latitude,
            @RequestParam float longitude,
            @RequestParam float radius) {

        String sql = "SELECT COUNT(*), AVG(rating), STDDEV_POP(rating) FROM restaurant WHERE ST_DWithin(ST_SetSRID(ST_MakePoint(lat,lng)::geography, 4326), ST_SetSRID(ST_MakePoint(?, ?)::geography, 4326), ?)";
        Map<String, Object> resultMap = jdbcTemplate.queryForMap(sql, latitude, longitude, radius);

        int count = ((Number) resultMap.get("count")).intValue();
        BigDecimal averageRatingBigDecimal = (BigDecimal) resultMap.get("avg");
        BigDecimal stdDevRatingBigDecimal = (BigDecimal) resultMap.get("stddev_pop");

        double averageRating = averageRatingBigDecimal != null ? averageRatingBigDecimal.doubleValue() : 0.0;
        double stdDevRating = stdDevRatingBigDecimal != null ? stdDevRatingBigDecimal.doubleValue() : 0.0;

        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("avg", averageRating);
        result.put("std", stdDevRating);

        return ResponseEntity.ok(result);
    }
        
}
