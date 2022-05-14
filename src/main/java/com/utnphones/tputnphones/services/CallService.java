package com.utnphones.tputnphones.services;

import com.utnphones.tputnphones.domain.Call;
import com.utnphones.tputnphones.repository.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CallService {

    @Autowired
    private CallRepository callRepository;

    public Call save(Call call){
        return callRepository.save(call);
    }

    public List<Call> findAll(){
        return callRepository.findAll();
    }

    public Call findById(Long id){
        return callRepository.findById(id).orElseThrow(()->new EntityNotFoundException("La llamada no existe"));
    }
}
