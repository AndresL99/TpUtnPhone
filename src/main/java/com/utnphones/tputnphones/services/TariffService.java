package com.utnphones.tputnphones.services;

import com.utnphones.tputnphones.domain.City;
import com.utnphones.tputnphones.domain.Tariff;
import com.utnphones.tputnphones.exception.TariffExistException;
import com.utnphones.tputnphones.exception.TariffNotExistException;
import com.utnphones.tputnphones.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TariffService {

    @Autowired
    private TariffRepository tariffRepository;
    private CityService cityService;

    public Tariff save(Tariff tariff){
        if(!tariffRepository.existsById(tariff.getIdTariff()))
        {
            return tariffRepository.save(tariff);
        }
        else
        {
            throw new TariffExistException("La tarifa que desea ingresar ya existe.");
        }

    }

    public List<Tariff> findAll(){
        return tariffRepository.findAll();
    }

    public Tariff findById(Long id){
        return tariffRepository.findById(id).orElseThrow(()->new EntityNotFoundException("La tarifa no existe"));
    }

    public Long findByCities(Long cityOrigin, Long cityDestination){
        return tariffRepository.getTariffByIds(cityOrigin,cityDestination);
    }

    public void putTariff(Long idTariff, Long idCity,Tariff tariff)
    {
        if(tariffRepository.existsById(tariff.getIdTariff()))
        {
            tariff = findById(idTariff);
            City city = cityService.findById(idCity);
            tariff.setOriginCity(city);
            tariffRepository.save(tariff);
        }
        else
        {
            throw new TariffNotExistException("Tarifa inexistente.");
        }
    }

}
