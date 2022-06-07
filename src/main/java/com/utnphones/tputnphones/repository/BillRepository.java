package com.utnphones.tputnphones.repository;

import com.utnphones.tputnphones.domain.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill,Long> {

}
