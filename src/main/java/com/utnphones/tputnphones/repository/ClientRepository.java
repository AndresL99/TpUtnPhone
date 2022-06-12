package com.utnphones.tputnphones.repository;

import com.utnphones.tputnphones.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import static com.utnphones.tputnphones.util.Querys.GET_CLIENT_USERNAME;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long>
{
    @Query(value = GET_CLIENT_USERNAME, nativeQuery = true)
    Client getClientByUsername(String username);
}
