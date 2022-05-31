package com.utnphones.tputnphones.repository;

import com.utnphones.tputnphones.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByDni(Integer dni);
    User findByUsernameAndPassword(String username, String password);
}
