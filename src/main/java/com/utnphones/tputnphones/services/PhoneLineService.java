package com.utnphones.tputnphones.services;

import com.utnphones.tputnphones.domain.phoneLine;
import com.utnphones.tputnphones.repository.PhoneLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PhoneLineService {

    @Autowired
    private PhoneLineRepository phoneLineRepository;

    public phoneLine save(phoneLine phoneLine){
        return phoneLineRepository.save(phoneLine);
    }

    public List<phoneLine> findAll(){
        return phoneLineRepository.findAll();
    }


    /*public phoneLine findById(S id){
        return phoneLineRepository.findById(id).orElseThrow(()->new EntityNotFoundException("El numero telefonico no existe"));
    }*/
}
