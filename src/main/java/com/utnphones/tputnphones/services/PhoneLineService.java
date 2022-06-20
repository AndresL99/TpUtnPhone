package com.utnphones.tputnphones.services;

import com.utnphones.tputnphones.domain.PhoneLine;
import com.utnphones.tputnphones.exception.PhoneLineExistException;
import com.utnphones.tputnphones.exception.PhoneLineNotExistException;
import com.utnphones.tputnphones.repository.PhoneLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class PhoneLineService {

    private PhoneLineRepository phoneLineRepository;

    @Autowired
    public PhoneLineService(PhoneLineRepository phoneLineRepository) {
        this.phoneLineRepository = phoneLineRepository;
    }

    public PhoneLine save(PhoneLine phoneLine){
        if(phoneLine.getPhoneNumber() != null)
        {
            return phoneLineRepository.save(phoneLine);
        }
        else
        {
            throw new PhoneLineExistException("La linea de telefono ya ha sido asignada a un usuario existente.");
        }

    }

    public PhoneLine getByPhoneNumber(String phoneNumber)
    {
        return phoneLineRepository.findById(phoneNumber).orElseThrow(()-> new PhoneLineNotExistException("La linea no existe."));
    }

    public List<PhoneLine> findAll(){
        return phoneLineRepository.findAll();
    }

    public void deletePhoneLine(String phoneNumber)
    {
        if(phoneLineRepository.existsByPhoneNumber(phoneNumber))
        {
            phoneLineRepository.deleteById(phoneNumber);
        }
        else
        {
            throw new PhoneLineNotExistException("La linea que desea borrar no se encuentra.");
        }
    }

}
