package com.utnphones.tputnphones.services;

import com.utnphones.tputnphones.domain.City;
import com.utnphones.tputnphones.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CityService {


    private CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City save(City city){
        return cityRepository.save(city);
    }

    public List<City> findAll(){
        return cityRepository.findAll();
    }

    public City findById(Long id){
        return cityRepository.findById(id).orElseThrow(()->new EntityNotFoundException("La ciudad no existe"));
    }
}
