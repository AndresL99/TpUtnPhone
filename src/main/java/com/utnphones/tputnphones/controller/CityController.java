package com.utnphones.tputnphones.controller;

import com.utnphones.tputnphones.domain.City;
import com.utnphones.tputnphones.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping(value = "/api/city")
@RestController
public class CityController {

    private CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping
    public ResponseEntity newCity(@RequestBody City city){
        City newCity = cityService.save(city);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idCity}")
                .buildAndExpand(newCity.getIdCity())
                .toUri();
        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<City>> findAll(){
        return ResponseEntity.ok(cityService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<City> findAllById(@PathVariable Long id){
        return ResponseEntity.ok(cityService.findById(id));
    }
}