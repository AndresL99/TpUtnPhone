package com.utnphones.tputnphones.repository;

import com.utnphones.tputnphones.domain.Call;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

import static com.utnphones.tputnphones.util.Querys.GET_CALLS_FOR_USER_CLIENT;
import static com.utnphones.tputnphones.util.Querys.GET_CALLS_FOR_USER_EMPLOYEE;

@Repository
public interface CallRepository extends JpaRepository<Call, Long> {

    @Query(value = GET_CALLS_FOR_USER_EMPLOYEE, nativeQuery = true)
    Page<Call>getCallByUserAndRank(Integer dni, Date start, Date end, Pageable pageable);

    @Query(value = GET_CALLS_FOR_USER_CLIENT, nativeQuery = true)
    Page<Call>getCallByClient(Long idClient, Date start, Date end, Pageable pageable);

}
