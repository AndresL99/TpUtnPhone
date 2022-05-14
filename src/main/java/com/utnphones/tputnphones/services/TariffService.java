package com.utnphones.tputnphones.services;

import com.utnphones.tputnphones.domain.Tariff;
import com.utnphones.tputnphones.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TariffService {

    @Autowired
    private TariffRepository tariffRepository;

    public Tariff save(Tariff tariff){
        return tariffRepository.save(tariff);
    }

    public List<Tariff> findAll(){
        return tariffRepository.findAll();
    }

    public Tariff findById(Long id){
        return tariffRepository.findById(id).orElseThrow(()->new EntityNotFoundException("La factura no existe"));
    }
}
