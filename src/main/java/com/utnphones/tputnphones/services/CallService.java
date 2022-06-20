package com.utnphones.tputnphones.services;

import com.utnphones.tputnphones.domain.Call;
import com.utnphones.tputnphones.domain.PhoneLine;
import com.utnphones.tputnphones.dto.CallDto;
import com.utnphones.tputnphones.exception.CallExistException;
import com.utnphones.tputnphones.exception.CallNotExistException;
import com.utnphones.tputnphones.repository.CallRepository;
import com.utnphones.tputnphones.repository.CityRepository;
import com.utnphones.tputnphones.repository.PhoneLineRepository;
import com.utnphones.tputnphones.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CallService {

    private CallRepository callRepository;

    private PhoneLineRepository phoneLineRepository;

    private CityRepository cityRepository;

    private TariffRepository tariffRepository;

    @Autowired
    public CallService(CallRepository callRepository, PhoneLineRepository phoneLineRepository, CityRepository cityRepository, TariffRepository tariffRepository) {
        this.callRepository = callRepository;
        this.phoneLineRepository = phoneLineRepository;
        this.cityRepository = cityRepository;
        this.tariffRepository = tariffRepository;
    }




    public Object save(CallDto incoming) throws ParseException {

        if(findByNumber(incoming.getOrigin()) && findByNumber(incoming.getDestination())){
            PhoneLine phone1 = PhoneLine.builder()
                    .phoneNumber(incoming.getOrigin())
                    .build();

            PhoneLine phone2 = PhoneLine.builder()
                    .phoneNumber(incoming.getDestination())
                    .build();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date parsedDate = dateFormat.parse(incoming.getDatetime());
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

            Call newCall = new Call();

            newCall.setOriginNumber(phone1);
            newCall.setDestinationNumber(phone2);
            newCall.setDayTime(timestamp);
            newCall.setDuration(incoming.getDuration());

            Long idOriginCity= cityRepository.getCityIdByPhoneNumber(incoming.getOrigin());
            newCall.setOriginCity(cityRepository.getById(idOriginCity));

            Long cityDestinationId= cityRepository.getCityIdByPhoneNumber(incoming.getDestination());
            newCall.setDestinationCity(cityRepository.getById(cityDestinationId));

            newCall.setTariff(tariffRepository.getTariffByCitiesId(idOriginCity,cityDestinationId));

            return callRepository.save(newCall);
        }
        else
        {
            throw new CallNotExistException("No se encontro el numero.");
        }

    }

    public List<Call> findAll(){
        return callRepository.findAll();
    }

    public Call findById(Long id){
        return callRepository.findById(id).orElseThrow(()->new EntityNotFoundException("La llamada no existe"));
    }

    public boolean findByNumber(String number){
        return phoneLineRepository.existsByPhoneNumber(number);
    }

    public Page<Call>getCallByUserAndRank(Integer dni, Date start, Date end, Pageable pageable)
    {
        return callRepository.getCallByUserAndRank(dni,start,end,pageable);
    }


    public Page<Call>getCallByClient(Long idClient, Date start, Date end, Pageable pageable)
    {
        return callRepository.getCallByClient(idClient,start,end,pageable);
    }



}
