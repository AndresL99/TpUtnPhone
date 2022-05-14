package com.utnphones.tputnphones.repository;

import com.utnphones.tputnphones.domain.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Long> {
}
