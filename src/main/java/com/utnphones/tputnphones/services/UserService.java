package com.utnphones.tputnphones.services;

import com.utnphones.tputnphones.domain.User;
import com.utnphones.tputnphones.exception.UserExistException;
import com.utnphones.tputnphones.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user){
        if(!userRepository.existsById(user.getDni()))
        {
            return userRepository.save(user);
        }
        else
        {
            throw new UserExistException("El usuario ya se encuentra registrado. ");
        }

    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findByDni(Integer dni){
        return userRepository.findByDni(dni).orElseThrow(()->new EntityNotFoundException("El usuario no existe"));
    }

    public User getByUsernameAndPassword(String username, String password)
    {
        return userRepository.findByUsernameAndPassword(username, password);
    }

}
