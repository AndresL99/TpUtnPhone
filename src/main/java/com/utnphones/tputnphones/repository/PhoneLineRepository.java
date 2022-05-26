package com.utnphones.tputnphones.repository;

import com.utnphones.tputnphones.domain.phoneLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneLineRepository extends JpaRepository<phoneLine, String> {

    boolean existsByPhoneNumber(String number);
}
