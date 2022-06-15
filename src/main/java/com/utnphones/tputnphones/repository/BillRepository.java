package com.utnphones.tputnphones.repository;

import com.utnphones.tputnphones.domain.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Date;

import static com.utnphones.tputnphones.util.Querys.GET_BILLS_FOR_USER_CLIENT;

@Repository
public interface BillRepository extends JpaRepository<Bill,Long> {

    @Query(value = GET_BILLS_FOR_USER_CLIENT,nativeQuery = true)
    Page<Bill>getBillByClient(Long idClient, Date start, Date end, Pageable pageable);

    @Procedure(procedureName = "get_clients_and_bill")
    void calculateBills();
}
