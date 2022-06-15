package com.utnphones.tputnphones.services;

import com.utnphones.tputnphones.domain.Bill;
import com.utnphones.tputnphones.domain.Call;
import com.utnphones.tputnphones.exception.BillExistException;
import com.utnphones.tputnphones.exception.BillNotExistException;
import com.utnphones.tputnphones.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

@Service
public class BillService {


    private BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public Bill save(Bill bill){
        if(!billRepository.existsById(bill.getIdBill()))
        {
            return billRepository.save(bill);
        }
        else
        {
            throw new BillExistException("La factura ya ha sido procesada.");
        }

    }

    public List<Bill> findAll(){
        return billRepository.findAll();
    }

    public Bill findById(Long id){
        return billRepository.findById(id).orElseThrow(()->new BillNotExistException("La factura no existe"));
    }

    public Page<Bill>getBillByClient(Long idClient, Date start, Date end, Pageable pageable)
    {
        return billRepository.getBillByClient(idClient,start,end,pageable);
    }

    public void generateBills(){
        billRepository.calculateBills();
    }

}
