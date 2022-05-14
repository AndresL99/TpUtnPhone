package com.utnphones.tputnphones.controller;

import com.utnphones.tputnphones.domain.City;
import com.utnphones.tputnphones.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RequestMapping(value = "/api/city")
@RestController
public class CityController {

    @Autowired
    private CityService cityService;

    @PostMapping
    public ResponseEntity newCity(@RequestBody City city, HttpServletRequest request){
        City newCity = cityService.save(city);
        URI location = ServletUriComponentsBuilder.
                fromServletMapping(request)
                .path("/api/city/" + newCity.getIdCity()).build()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<City>> findAll(){
        return ResponseEntity.ok(cityService.findAll());
    }

    @GetMapping(value = "/{cityId}")
    public ResponseEntity<City> findAllById(@PathVariable Long id){
        return ResponseEntity.ok(cityService.findById(id));
    }
}
