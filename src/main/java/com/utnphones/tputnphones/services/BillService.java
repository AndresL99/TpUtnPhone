package com.utnphones.tputnphones.services;

import com.utnphones.tputnphones.domain.Bill;
import com.utnphones.tputnphones.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    public Bill save(Bill bill){
        return billRepository.save(bill);
    }

    public List<Bill> findAll(){
        return billRepository.findAll();
    }

    public Bill findById(Long id){
        return billRepository.findById(id).orElseThrow(()->new EntityNotFoundException("La factura no existe"));
    }
}
