package com.utnphones.tputnphones.service;

import com.utnphones.tputnphones.domain.City;
import com.utnphones.tputnphones.repository.CityRepository;
import com.utnphones.tputnphones.services.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static com.utnphones.tputnphones.utils.TestEntityFactory.getCities;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getCity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CityServiceTest
{
    private CityRepository cityRepository;
    private CityService cityService;

    @BeforeEach
    public void setUp()
    {
        cityRepository = mock(CityRepository.class);
        cityService = new CityService(cityRepository);
    }

    @Test
    void addCityTest()
    {
        when(cityRepository.save(getCity())).thenReturn(getCity());

        City city = cityService.save(getCity());

        assertEquals(getCity(),city);
    }

    @Test
    void getAllTest()
    {
        when(cityRepository.findAll()).thenReturn(getCities());

        List<City>cities = cityService.findAll();

        assertEquals(getCities().size(),cities.size());
        verify(cityRepository,times(1)).findAll();
    }

    @Test
    void getCityByIdOk()
    {
        Long idCity = getCity().getIdCity();

        when(cityRepository.findById(idCity)).thenReturn(Optional.of(getCity()));

        City city = cityService.findById(idCity);

        assertEquals(getCity(),city);
        verify(cityRepository,times(1)).findById(idCity);
    }

    @Test
    void getCityByIdFail()
    {
        when(cityRepository.findById(50L)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class,()->cityService.findById(50L));
    }
}
