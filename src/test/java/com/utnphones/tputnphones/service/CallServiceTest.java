package com.utnphones.tputnphones.service;

import com.utnphones.tputnphones.domain.Call;
import com.utnphones.tputnphones.repository.CallRepository;
import com.utnphones.tputnphones.repository.CityRepository;
import com.utnphones.tputnphones.repository.PhoneLineRepository;
import com.utnphones.tputnphones.repository.TariffRepository;
import com.utnphones.tputnphones.services.CallService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.utnphones.tputnphones.utils.TestEntityFactory.aPageable;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getCall;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getCallPage;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getCalls;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getClient;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getTelephoneLine;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CallServiceTest
{
    private CallRepository callRepository;
    private CallService callService;
    private PhoneLineRepository phoneLineRepository;
    private CityRepository cityRepository;
    private TariffRepository tariffRepository;

    @BeforeEach
    public void setUp()
    {
        callRepository = mock(CallRepository.class);
        phoneLineRepository = mock(PhoneLineRepository.class);
        cityRepository = mock(CityRepository.class);
        tariffRepository = mock(TariffRepository.class);
        callService = new CallService(callRepository,phoneLineRepository,cityRepository,tariffRepository);
    }

    @Test
    void getCallByClientTest()
    {
        Long idClient = getClient().getIdClient();
        Date start = new Date();
        Date end = new Date();
        when(callRepository.getCallByClient(idClient,start,end,aPageable())).thenReturn(getCallPage());

        Page<Call>callPage = callService.getCallByClient(idClient,start,end,aPageable());

        assertNotNull(callPage);
        assertEquals(getCallPage().getTotalElements(),callPage.getTotalElements());
        verify(callRepository,times(1)).getCallByClient(idClient,start,end,aPageable());
    }

    @Test
    void getCallByUserAndRankTest()
    {
        Integer dni = getUser().getDni();
        Date start = new Date();
        Date end = new Date();
        when(callRepository.getCallByUserAndRank(dni,start,end,aPageable())).thenReturn(getCallPage());

        Page<Call>callPage = callService.getCallByUserAndRank(dni,start,end,aPageable());

        assertNotNull(callPage);
        assertEquals(getCallPage().getTotalElements(),callPage.getTotalElements());
        verify(callRepository,times(1)).getCallByUserAndRank(dni,start,end,aPageable());
    }

    @Test
    void findByNumberTest()
    {
        String number = getTelephoneLine().getPhoneNumber();
        when(phoneLineRepository.existsByPhoneNumber(number)).thenReturn(true);

        Boolean isExist = callService.findByNumber(number);

        verify(phoneLineRepository,times(1)).existsByPhoneNumber(number);
    }

    @Test
    void findByIdTestOk()
    {
        Long idCall = getCall().getIdCall();
        when(callRepository.findById(idCall)).thenReturn(Optional.of(getCall()));

        Call call = callService.findById(idCall);

        assertNotNull(call);
        assertEquals(getCall().getIdCall(),call.getIdCall());
        verify(callRepository,times(1)).findById(idCall);
    }

    @Test
    void findByIdTestFail()
    {
        when(callRepository.findById(77L)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class,()->callService.findById(77L));
    }

    @Test
    void findAllTest()
    {
        when(callRepository.findAll()).thenReturn(getCalls());

        List<Call>calls = callService.findAll();

        assertNotNull(calls);
        assertEquals(getCalls().size(),calls.size());
        verify(callRepository,times(1)).findAll();

    }

    @Test
    void saveTestOk()
    {

    }
}
