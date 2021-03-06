package com.utnphones.tputnphones.service;

import com.utnphones.tputnphones.domain.PhoneLine;
import com.utnphones.tputnphones.exception.PhoneLineExistException;
import com.utnphones.tputnphones.exception.PhoneLineNotExistException;
import com.utnphones.tputnphones.repository.PhoneLineRepository;
import com.utnphones.tputnphones.services.PhoneLineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.utnphones.tputnphones.utils.TestEntityFactory.getPhoneLines;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getTariff;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getTelephoneLine;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PhoneLineServiceTest
{
    private PhoneLineRepository phoneLineRepository;
    private PhoneLineService phoneLineService;

    @BeforeEach
    public void setUp()
    {
        phoneLineRepository = mock(PhoneLineRepository.class);
        phoneLineService = new PhoneLineService(phoneLineRepository);
    }

    @Test
    void addPhoneLineTest()
    {
        when(phoneLineRepository.findById(anyString())).thenReturn(Optional.empty());
        when(phoneLineRepository.save(getTelephoneLine())).thenReturn(getTelephoneLine());

        PhoneLine phoneLine = phoneLineService.save(getTelephoneLine());

        assertEquals(getTelephoneLine(),phoneLine);
    }

    @Test
    void addPhoneLineTestFail()
    {
        when(phoneLineRepository.findById(anyString())).thenReturn(Optional.ofNullable(getTelephoneLine()));
        assertThrows(PhoneLineExistException.class,()->phoneLineService.save(getTelephoneLine()));
    }

    @Test
    void findAllTest()
    {
        when(phoneLineRepository.findAll()).thenReturn(getPhoneLines());

        List<PhoneLine>phoneLines = phoneLineService.findAll();

        assertEquals(getPhoneLines(),phoneLines);
        verify(phoneLineRepository,times(1)).findAll();
    }

    @Test
    void deleteTestOk()
    {
        String phoneNumber = getTelephoneLine().getPhoneNumber();
        when(phoneLineRepository.existsByPhoneNumber(phoneNumber)).thenReturn(true);
        doNothing().when(phoneLineRepository).deleteById(phoneNumber);

        phoneLineService.deletePhoneLine(phoneNumber);

        verify(phoneLineRepository,times(1)).deleteById(phoneNumber);
    }

    @Test
    void deleteTestFailed()
    {
        when(phoneLineRepository.existsByPhoneNumber("2241222333")).thenReturn(false);

        assertThrows(PhoneLineNotExistException.class,()->phoneLineService.deletePhoneLine("2241222333"));
    }

    @Test
    void getByPhoneNumberTest(){
        String phoneNumber = getTelephoneLine().getPhoneNumber();
        when(phoneLineRepository.findById(phoneNumber)).thenReturn(Optional.ofNullable(getTelephoneLine()));
        PhoneLine phoneLine = phoneLineService.getByPhoneNumber(phoneNumber);
        assertEquals(getTelephoneLine(),phoneLine);
    }

    @Test
    void getByPhoneNumberTestFailed()
    {
        when(phoneLineRepository.findById("2241222333")).thenThrow(PhoneLineNotExistException.class);

        assertThrows(PhoneLineNotExistException.class,()->phoneLineService.getByPhoneNumber("2241222333"));
    }

}
