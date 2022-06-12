package com.utnphones.tputnphones.services;

import com.utnphones.tputnphones.domain.PhoneLine;
import com.utnphones.tputnphones.exception.PhoneLineExistException;
import com.utnphones.tputnphones.exception.PhoneLineNotExistException;
import com.utnphones.tputnphones.repository.PhoneLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneLineService {

    @Autowired
    private PhoneLineRepository phoneLineRepository;

    public PhoneLine save(PhoneLine phoneLine){
        if(!phoneLineRepository.existsByPhoneNumber(phoneLine.getPhoneNumber()))
        {
            return phoneLineRepository.save(phoneLine);
        }
        else
        {
            throw new PhoneLineExistException("La linea de telefono ya ha sido asignada a un usuario existente.");
        }

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
