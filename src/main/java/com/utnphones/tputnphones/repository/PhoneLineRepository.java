package com.utnphones.tputnphones.repository;

import com.utnphones.tputnphones.domain.PhoneLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneLineRepository extends JpaRepository<PhoneLine, String> {

    boolean existsByPhoneNumber(String number);
}
