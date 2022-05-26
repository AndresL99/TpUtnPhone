package com.utnphones.tputnphones.repository;

import com.utnphones.tputnphones.domain.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import static com.utnphones.tputnphones.util.Querys.GET_TARIFF_BY_CITIES_ID;
import static com.utnphones.tputnphones.util.Querys.GET_TARIFF_BY_NAMES;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Long> {

    @Query(value = GET_TARIFF_BY_NAMES, nativeQuery = true)
    Long getTariffByIds(Long originCity, Long destinationCity);

    @Query(value = GET_TARIFF_BY_CITIES_ID, nativeQuery = true)
    Tariff getTariffByCitiesId(Long originCity, Long destinationCity);
}
