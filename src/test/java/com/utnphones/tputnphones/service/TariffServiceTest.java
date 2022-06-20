package com.utnphones.tputnphones.service;

import com.utnphones.tputnphones.domain.Tariff;
import com.utnphones.tputnphones.exception.TariffExistException;
import com.utnphones.tputnphones.exception.TariffNotExistException;
import com.utnphones.tputnphones.repository.TariffRepository;
import com.utnphones.tputnphones.services.CityService;
import com.utnphones.tputnphones.services.TariffService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.utnphones.tputnphones.utils.TestEntityFactory.getCity;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getTariff;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getTariffs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TariffServiceTest
{
    private TariffRepository tariffRepository;
    private TariffService tariffService;
    private CityService cityService;

    @BeforeEach
    public void setUp()
    {
        tariffRepository = mock(TariffRepository.class);
        cityService = mock(CityService.class);
        tariffService = new TariffService(tariffRepository,cityService);
    }

    @Test
    void addTariffTestOk()
    {
        when(tariffRepository.save(getTariff())).thenReturn(getTariff());

        Tariff tariff = tariffService.save(getTariff());

        assertEquals(getTariff(),tariff);
    }

    @Test
    void addTariffTestFail()
    {
        when(tariffRepository.save(getTariff())).thenThrow(TariffExistException.class);
        assertThrows(TariffExistException.class,()-> tariffService.save(getTariff()));
    }

    @Test
    void findAllTest()
    {
        when(tariffRepository.findAll()).thenReturn(getTariffs());

        List<Tariff>tariffs = tariffService.findAll();

        assertEquals(getTariffs(),tariffs);
        verify(tariffRepository,times(1)).findAll();
    }

    @Test
    void findByIdTestOk()
    {
        Long idTariff = getTariff().getIdTariff();
        when(tariffRepository.findById(idTariff)).thenReturn(Optional.of(getTariff()));

        Tariff tariff = tariffService.findById(idTariff);

        assertEquals(getTariff(),tariff);
        verify(tariffRepository,times(1)).findById(idTariff);
    }

    @Test
    void findByIdTestFail()
    {
        when(tariffRepository.findById(7L)).thenThrow(TariffNotExistException.class);
        assertThrows(TariffNotExistException.class,()->tariffService.findById(7L));
    }

    @Test
    void findByCitiesTest()
    {
        Long idOriginCity = getTariff().getOriginCity().getIdCity();
        Long idDestinationCity = getTariff().getDestinationCity().getIdCity();
        when(tariffRepository.getTariffByCitiesId(idOriginCity,idDestinationCity)).thenReturn(getTariff());

        Long find = tariffService.findByCities(idOriginCity,idDestinationCity);

        verify(tariffRepository,times(1)).getTariffByIds(idOriginCity,idDestinationCity);
    }

    @Test
    void putTariffTestOk()
    {
        Long idTariff = getTariff().getIdTariff();
        when(tariffRepository.existsById(idTariff)).thenReturn(true);
        when(tariffRepository.findById(idTariff)).thenReturn(Optional.of(getTariff()));
        when(cityService.findById(getCity().getIdCity())).thenReturn(getCity());
        when(tariffRepository.save(getTariff())).thenReturn(getTariff());

        tariffService.putTariff(idTariff,getCity().getIdCity(),getTariff());

        verify(tariffRepository,times(1)).findById(idTariff);
    }

    @Test
    void putTariffTestFail()
    {
        when(tariffRepository.existsById(10L)).thenReturn(false);
        when(tariffRepository.existsById(10L)).thenThrow(TariffNotExistException.class);
        assertThrows(TariffNotExistException.class,()->tariffService.putTariff(10L,1L,getTariff()));
    }

}
