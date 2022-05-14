package com.utnphones.tputnphones.repository;

import com.utnphones.tputnphones.domain.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallRepository extends JpaRepository<Call, Long> {

}
