package com.utnphones.tputnphones.repository;

import com.utnphones.tputnphones.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import static com.utnphones.tputnphones.util.Querys.GET_CITY_ID_BY_PHONE_NUMBER;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query(value = GET_CITY_ID_BY_PHONE_NUMBER, nativeQuery = true)
    Long getCityIdByPhoneNumber(String phoneNumber);
}
